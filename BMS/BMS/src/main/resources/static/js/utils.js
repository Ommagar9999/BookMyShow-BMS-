// ===== TOAST NOTIFICATIONS =====
function showToast(message, type = 'info') {
    let container = document.querySelector('.toast-container');

    if (!container) {
        container = document.createElement('div');
        container.className = 'toast-container';
        document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.textContent = message;

    container.appendChild(toast);

    setTimeout(() => toast.remove(), 3000);
}


// ===== SESSION (simple localStorage) =====
function setLoggedInUser(user) {
    localStorage.setItem('bms_user', JSON.stringify(user));
    updateNavUser();
}

function getLoggedInUser() {
    const u = localStorage.getItem('bms_user');
    return u ? JSON.parse(u) : null;
}

function logout() {
    localStorage.removeItem('bms_user');
    updateNavUser();
    showToast('Logged out successfully', 'success');

    if (window.location.pathname.includes('bookings')) {
        window.location.href = '../index.html';
    }
}


// ===== NAV UPDATE =====
function updateNavUser() {
    const navUser = document.getElementById('nav-user');
    if (!navUser) return;

    const user = getLoggedInUser();

    if (user) {
        navUser.innerHTML = `
            <span style="color: var(--text-muted); font-size: 0.9rem;">
                Hi, <strong style="color: var(--white)">${user.name}</strong>
            </span>
            <button class="btn btn-sm btn-outline" onclick="logout()">Logout</button>
        `;
    } else {
        navUser.innerHTML = `
            <a href="${getPagePath('pages/login.html')}" class="btn btn-sm btn-outline">Login</a>
            <a href="${getPagePath('pages/register.html')}" class="btn btn-sm btn-primary">Sign Up</a>
        `;
    }
}


// ===== PATH HELPERS =====
function getPagePath(path) {
    if (window.location.pathname.includes('/pages/')) {
        return path.replace('pages/', '').replace('../', '');
    }
    return path;
}

function getBasePath() {
    return window.location.pathname.includes('/pages/') ? '../' : './';
}


// ===== UI HELPERS =====
function showLoading(containerId) {
    const el = document.getElementById(containerId);
    if (el) el.innerHTML = '<div class="loading-center"><div class="spinner"></div></div>';
}

function showEmpty(containerId, message = 'No data found') {
    const el = document.getElementById(containerId);
    if (el) {
        el.innerHTML = `<div class="empty-state"><div class="icon">🎬</div><p>${message}</p></div>`;
    }
}


// ===== MODAL =====
function openModal(modalId) {
    document.getElementById(modalId)?.classList.add('active');
}

function closeModal(modalId) {
    document.getElementById(modalId)?.classList.remove('active');
}

document.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal-overlay')) {
        e.target.classList.remove('active');
    }
});


// ===== INIT =====
document.addEventListener('DOMContentLoaded', () => {
    updateNavUser();
});


// ===== MOVIE VALIDATION =====
function isValidPoster(url) {
    return /\.(jpg|jpeg|png|webp)$/i.test(url);
}


// ===== ADD MOVIE =====
function addMovie() {

    const title = document.getElementById("movie-title").value;
    const genre = document.getElementById("movie-genre").value;
    const language = document.getElementById("movie-language").value;
    const duration = document.getElementById("movie-duration").value;
    const rating = document.getElementById("movie-rating").value;
    const releaseDate = document.getElementById("movie-release").value;
    const description = document.getElementById("movie-desc").value;
    const poster = document.getElementById("movie-poster").value;

    if (!title || !genre || !language) {
        showToast("Title, Genre, Language required", "error");
        return;
    }

    if (!poster) {
        showToast("Poster URL required", "error");
        return;
    }

    if (!isValidPoster(poster)) {
        showToast("Poster must end with jpg/jpeg/png/webp", "error");
        return;
    }

    const data = {
        title,
        genre,
        language,
        durationMinutes: duration,
        rating,
        releaseDate,
        description,
        posterUrl: poster
    };

    MovieAPI.add(data)
        .then(() => {
            showToast("Movie added successfully", "success");
            loadMovies();
        })
        .catch(() => {
            showToast("Failed to add movie", "error");
        });
}


// ===== RENDER MOVIES =====
function renderMovies(data) {

    if (!data || data.length === 0) {
        document.getElementById("movies-table").innerHTML =
            "<p style='color:var(--text-muted)'>No movies found</p>";
        return;
    }

    let html = `
        <table border="1" width="100%" cellpadding="10">
            <tr>
                <th>ID</th>
                <th>Poster</th>
                <th>Title</th>
                <th>Genre</th>
                <th>Language</th>
                <th>Rating</th>
                <th>Action</th>
            </tr>
    `;

    data.forEach(movie => {
        html += `
            <tr>
                <td>${movie.id}</td>
                <td>
                    <img src="${movie.posterUrl}"
                         style="width:50px;height:70px;object-fit:cover;border-radius:5px;">
                </td>
                <td>${movie.title}</td>
                <td>${movie.genre}</td>
                <td>${movie.language}</td>
                <td>${movie.rating}</td>
                <td>
                    <button class="btn btn-primary" onclick="editMovie(${movie.id})">
                        Update
                    </button>
                    <button class="btn btn-danger" onclick="deleteMovie(${movie.id})">
                        Delete
                    </button>
                </td>
            </tr>
        `;
    });

    html += `</table>`;
    document.getElementById("movies-table").innerHTML = html;
}


let confirmCallback = null;

function showConfirm(message, onYes) {
    document.getElementById('confirm-message').innerText = message;
    document.getElementById('confirm-modal').classList.remove('hidden');
    confirmCallback = onYes;
}

function closeConfirm() {
    document.getElementById('confirm-modal').classList.add('hidden');
    confirmCallback = null;
}

document.addEventListener('DOMContentLoaded', () => {
    const btn = document.getElementById('confirm-yes');

    if (btn) {
        btn.addEventListener('click', () => {
            if (confirmCallback) confirmCallback();
            closeConfirm();
        });
    }
});

