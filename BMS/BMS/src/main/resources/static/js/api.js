// ===== API BASE URL =====
const API = 'http://localhost:8080/api';

// ===== GENERIC FETCH HELPERS =====
async function apiGet(endpoint) {
    const res = await fetch(`${API}${endpoint}`);
    if (!res.ok) {
        const err = await res.json().catch(() => ({ message: res.statusText }));
        throw new Error(err.message || 'Request failed');
    }
    return res.json();
}

async function apiPost(endpoint, data) {
    const res = await fetch(`${API}${endpoint}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    if (!res.ok) {
        const err = await res.json().catch(() => ({ message: res.statusText }));
        throw new Error(err.message || 'Request failed');
    }
    return res.json();
}

async function apiPut(endpoint, data) {
    const res = await fetch(`${API}${endpoint}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: data ? JSON.stringify(data) : undefined
    });
    if (!res.ok) {
        const err = await res.json().catch(() => ({ message: res.statusText }));
        throw new Error(err.message || 'Request failed');
    }
    return res.json();
}

async function apiDelete(endpoint) {
    const res = await fetch(`${API}${endpoint}`, { method: 'DELETE' });
    if (!res.ok) {
        const err = await res.json().catch(() => ({ message: res.statusText }));
        throw new Error(err.message || 'Request failed');
    }
    // might return text
    const text = await res.text();
    try { return JSON.parse(text); } catch { return text; }
}

// ===== USER APIs =====
const UserAPI = {
    register: (data) => apiPost('/users/register', data),
    login: (data) => apiPost('/users/login', data),
    getAll: () => apiGet('/users'),
    getById: (id) => apiGet(`/users/${id}`)
};


// ===== CITY APIs =====
const CityAPI = {
    // Create City (matches /addCity)
    add: (data) => apiPost('/cities/addCity', data),

    // Get all cities
    getAll: () => apiGet('/cities'),

    // Get city by id
    getById: (id) => apiGet(`/cities/${id}`),

    delete: (id) => apiDelete(`/cities/${id}`)

};

// ===== MOVIE APIs =====
const MovieAPI = {

    add: (movieRequest, posterFile) => {
        const formData = new FormData();

        formData.append(
            "request",
            new Blob([JSON.stringify(movieRequest)], {
                type: "application/json"
            })
        );

        formData.append("poster", posterFile);

        return fetch(`${API}/movies/add`, {
            method: "POST",
            body: formData
        }).then(async res => {
            if (!res.ok) {
                const err = await res.text();
                throw new Error(err || "Upload failed");
            }
            return res.json();
        });
    },

    getAll: () => apiGet('/movies'),

    getById: (id) => apiGet(`/movies/${id}`),

    search: (title) =>
        apiGet(`/movies/search?title=${encodeURIComponent(title)}`),

    getByGenre: (genre) =>
        apiGet(`/movies/genre?genre=${genre}`),

    getByLanguage: (lang) =>
        apiGet(`/movies/language?language=${lang}`),

    update: (id, data) =>
        apiPut(`/movies/${id}`, data),

    delete: (id) =>
        apiDelete(`/movies/${id}`)
};

// ===== THEATER APIs =====
const TheaterAPI = {
    add: (data) => apiPost('/theaters/addTheater', data),
    getAll: () => apiGet('/theaters'),
    getById: (id) => apiGet(`/theaters/${id}`),
    getByCity: (cityId) => apiGet(`/theaters/city/${cityId}`)
};

// ===== SCREEN APIs =====
const ScreenAPI = {
    add: (data) => apiPost('/screens/addScreen', data),
    getAll: () => apiGet('/screens'),
    getById: (id) => apiGet(`/screens/${id}`),
    getByTheater: (theaterId) => apiGet(`/screens/theater/${theaterId}`)
};

// ===== SEAT APIs =====
const SeatAPI = {
    add: (data) => apiPost('/seats/addSeat', data),
    getByScreen: (screenId) => apiGet(`/seats/screen/${screenId}`),
    getById: (id) => apiGet(`/seats/${id}`)
};

// ===== SHOW APIs =====
const ShowAPI = {
    add: (data) => apiPost('/shows/addShow', data),
    getAll: () => apiGet('/shows'),
    getById: (id) => apiGet(`/shows/${id}`),
    getByMovie: (movieId) => apiGet(`/shows/movie/${movieId}`),
    getByMovieAndDate: (movieId, date) => apiGet(`/shows/movie/${movieId}/date?date=${date}`)
};

// ===== BOOKING APIs =====
const BookingAPI = {
    create: (data) => apiPost('/bookings', data),
    getById: (id) => apiGet(`/bookings/${id}`),
    getByUser: (userId) => apiGet(`/bookings/user/${userId}`),
    cancel: (id) => apiPut(`/bookings/${id}/cancel`),
    getAvailableSeats: (showId) => apiGet(`/bookings/show/${showId}/available-seats`)
};