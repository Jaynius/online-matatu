// API Base URL - Update this to match your Spring Boot application URL
const API_BASE_URL = 'http://localhost:8080/api/v1';

// Global state
let currentData = {
    matatus: [],
    passengers: [],
    routes: [],
    tickets: [],
    payments: [],
    gpsLocations: []
};

// Authentication state
let currentUser = null;
let authToken = localStorage.getItem('authToken');

// DOM Elements
const modal = document.getElementById('modal');
const modalTitle = document.getElementById('modal-title');
const form = document.getElementById('form');
const loading = document.getElementById('loading');

// Navigation functionality
document.querySelectorAll('.nav-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        const section = btn.getAttribute('data-section');
        showSection(section);
        
        // Update active button
        document.querySelectorAll('.nav-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
    });
});

function showSection(sectionId) {
    // Hide all sections
    document.querySelectorAll('.section').forEach(section => {
        section.classList.remove('active');
    });
    
    // Show target section
    document.getElementById(sectionId).classList.add('active');
    
    // Load data for the section
    loadSectionData(sectionId);
}

function loadSectionData(sectionId) {
    // Check if user is authenticated
    if (!authToken) {
        showError('Please login to access this section');
        return;
    }
    
    switch(sectionId) {
        case 'dashboard':
            loadDashboardData();
            break;
        case 'matatus':
            if (currentUser.role === 'ADMIN') {
                loadMatatus();
            } else {
                showError('Access denied. Admin only.');
            }
            break;
        case 'passengers':
            if (currentUser.role === 'ADMIN') {
                loadPassengers();
            } else {
                showError('Access denied. Admin only.');
            }
            break;
        case 'routes':
            if (currentUser.role === 'ADMIN') {
                loadRoutes();
            } else {
                showError('Access denied. Admin only.');
            }
            break;
        case 'tickets':
            loadTickets();
            break;
        case 'payments':
            loadPayments();
            break;
        case 'gps':
            if (currentUser.role === 'ADMIN') {
                loadGpsLocations();
            } else {
                showError('Access denied. Admin only.');
            }
            break;
    }
}

// Dashboard functionality
async function loadDashboardData() {
    showLoading();
    try {
        const [matatus, passengers, routes, tickets] = await Promise.all([
            fetchData('/matatu'),
            fetchData('/passenger'),
            fetchData('/route'),
            fetchData('/ticket')
        ]);
        
        document.getElementById('matatu-count').textContent = matatus.length;
        document.getElementById('passenger-count').textContent = passengers.length;
        document.getElementById('route-count').textContent = routes.length;
        document.getElementById('ticket-count').textContent = tickets.length;
        
        // Generate recent activity
        generateRecentActivity();
        
    } catch (error) {
        console.error('Error loading dashboard data:', error);
        showError('Failed to load dashboard data');
    } finally {
        hideLoading();
    }
}

function generateRecentActivity() {
    const activityFeed = document.getElementById('activity-feed');
    const activities = [
        { icon: 'fas fa-bus', title: 'New Matatu Registered', desc: 'License: KCA 123A' },
        { icon: 'fas fa-users', title: 'Passenger Registered', desc: 'John Doe joined' },
        { icon: 'fas fa-ticket-alt', title: 'Ticket Booked', desc: 'Seat 15 on Route 1' },
        { icon: 'fas fa-credit-card', title: 'Payment Processed', desc: 'KES 150.00' },
        { icon: 'fas fa-route', title: 'New Route Added', desc: 'Nairobi to Mombasa' }
    ];
    
    activityFeed.innerHTML = activities.map(activity => `
        <div class="activity-item">
            <div class="activity-icon">
                <i class="${activity.icon}"></i>
            </div>
            <div class="activity-content">
                <h4>${activity.title}</h4>
                <p>${activity.desc}</p>
            </div>
        </div>
    `).join('');
}

// Matatu functionality
async function loadMatatus() {
    showLoading();
    try {
        const matatus = await fetchData('/matatu');
        currentData.matatus = matatus;
        renderMatatuTable(matatus);
    } catch (error) {
        console.error('Error loading matatus:', error);
        showError('Failed to load matatus');
    } finally {
        hideLoading();
    }
}

function renderMatatuTable(matatus) {
    const tbody = document.getElementById('matatu-table-body');
    tbody.innerHTML = matatus.map(matatu => `
        <tr>
            <td>${matatu.driverName || 'N/A'}</td>
            <td>${matatu.licenceNumber || 'N/A'}</td>
            <td>${matatu.capacity || 'N/A'}</td>
            <td>${matatu.route?.routeName || 'N/A'}</td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-sm btn-secondary" onclick="editMatatu('${matatu.licenceNumber}')">
                        <i class="fas fa-edit"></i> Edit
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteMatatu('${matatu.licenceNumber}')">
                        <i class="fas fa-trash"></i> Delete
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function showMatatuForm(matatu = null) {
    modalTitle.textContent = matatu ? 'Edit Matatu' : 'Add New Matatu';
    
    form.innerHTML = `
        <div class="form-group">
            <label for="driverName">Driver Name</label>
            <input type="text" id="driverName" name="driverName" value="${matatu?.driverName || ''}" required>
        </div>
        <div class="form-group">
            <label for="licenceNumber">License Number</label>
            <input type="text" id="licenceNumber" name="licenceNumber" value="${matatu?.licenceNumber || ''}" required>
        </div>
        <div class="form-group">
            <label for="capacity">Capacity</label>
            <input type="number" id="capacity" name="capacity" value="${matatu?.capacity || ''}" required>
        </div>
        <div class="form-actions">
            <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancel</button>
            <button type="submit" class="btn btn-primary">
                ${matatu ? 'Update' : 'Create'} Matatu
            </button>
        </div>
    `;
    
    form.onsubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());
        
        if (matatu) {
            updateMatatu(matatu.licenceNumber, data);
        } else {
            createMatatu(data);
        }
    };
    
    showModal();
}

async function createMatatu(data) {
    showLoading();
    try {
        await fetchData('/matatu', {
            method: 'POST',
            body: JSON.stringify(data)
        });
        closeModal();
        loadMatatus();
        showSuccess('Matatu created successfully');
    } catch (error) {
        console.error('Error creating matatu:', error);
        showError('Failed to create matatu');
    } finally {
        hideLoading();
    }
}

async function updateMatatu(licenceNumber, data) {
    showLoading();
    try {
        await fetchData(`/matatu/${licenceNumber}`, {
            method: 'PATCH',
            body: JSON.stringify(data)
        });
        closeModal();
        loadMatatus();
        showSuccess('Matatu updated successfully');
    } catch (error) {
        console.error('Error updating matatu:', error);
        showError('Failed to update matatu');
    } finally {
        hideLoading();
    }
}

async function deleteMatatu(licenceNumber) {
    if (!confirm('Are you sure you want to delete this matatu?')) return;
    
    showLoading();
    try {
        await fetchData(`/matatu/${licenceNumber}`, { method: 'DELETE' });
        loadMatatus();
        showSuccess('Matatu deleted successfully');
    } catch (error) {
        console.error('Error deleting matatu:', error);
        showError('Failed to delete matatu');
    } finally {
        hideLoading();
    }
}

function editMatatu(licenceNumber) {
    const matatu = currentData.matatus.find(m => m.licenceNumber === licenceNumber);
    if (matatu) {
        showMatatuForm(matatu);
    }
}

// Passenger functionality
async function loadPassengers() {
    showLoading();
    try {
        const passengers = await fetchData('/passenger');
        currentData.passengers = passengers;
        renderPassengerTable(passengers);
    } catch (error) {
        console.error('Error loading passengers:', error);
        showError('Failed to load passengers');
    } finally {
        hideLoading();
    }
}

function renderPassengerTable(passengers) {
    const tbody = document.getElementById('passenger-table-body');
    tbody.innerHTML = passengers.map(passenger => `
        <tr>
            <td>${passenger.passengerName || 'N/A'}</td>
            <td>${passenger.passengerEmail || 'N/A'}</td>
            <td>${passenger.passengerPhone || 'N/A'}</td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-sm btn-secondary" onclick="editPassenger(${passenger.id})">
                        <i class="fas fa-edit"></i> Edit
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deletePassenger(${passenger.id})">
                        <i class="fas fa-trash"></i> Delete
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function showPassengerForm(passenger = null) {
    modalTitle.textContent = passenger ? 'Edit Passenger' : 'Add New Passenger';
    
    form.innerHTML = `
        <div class="form-group">
            <label for="passengerName">Passenger Name</label>
            <input type="text" id="passengerName" name="passengerName" value="${passenger?.passengerName || ''}" required>
        </div>
        <div class="form-group">
            <label for="passengerEmail">Email</label>
            <input type="email" id="passengerEmail" name="passengerEmail" value="${passenger?.passengerEmail || ''}" required>
        </div>
        <div class="form-group">
            <label for="passengerPhone">Phone</label>
            <input type="tel" id="passengerPhone" name="passengerPhone" value="${passenger?.passengerPhone || ''}" required>
        </div>
        <div class="form-actions">
            <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancel</button>
            <button type="submit" class="btn btn-primary">
                ${passenger ? 'Update' : 'Create'} Passenger
            </button>
        </div>
    `;
    
    form.onsubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());
        
        if (passenger) {
            updatePassenger(passenger.id, data);
        } else {
            createPassenger(data);
        }
    };
    
    showModal();
}

async function createPassenger(data) {
    showLoading();
    try {
        await fetchData('/passenger', {
            method: 'POST',
            body: JSON.stringify(data)
        });
        closeModal();
        loadPassengers();
        showSuccess('Passenger created successfully');
    } catch (error) {
        console.error('Error creating passenger:', error);
        showError('Failed to create passenger');
    } finally {
        hideLoading();
    }
}

async function updatePassenger(id, data) {
    showLoading();
    try {
        await fetchData(`/passenger/${id}`, {
            method: 'PATCH',
            body: JSON.stringify(data)
        });
        closeModal();
        loadPassengers();
        showSuccess('Passenger updated successfully');
    } catch (error) {
        console.error('Error updating passenger:', error);
        showError('Failed to update passenger');
    } finally {
        hideLoading();
    }
}

async function deletePassenger(id) {
    if (!confirm('Are you sure you want to delete this passenger?')) return;
    
    showLoading();
    try {
        await fetchData(`/passenger/${id}`, { method: 'DELETE' });
        loadPassengers();
        showSuccess('Passenger deleted successfully');
    } catch (error) {
        console.error('Error deleting passenger:', error);
        showError('Failed to delete passenger');
    } finally {
        hideLoading();
    }
}

function editPassenger(id) {
    const passenger = currentData.passengers.find(p => p.id === id);
    if (passenger) {
        showPassengerForm(passenger);
    }
}

// Route functionality
async function loadRoutes() {
    showLoading();
    try {
        const routes = await fetchData('/route');
        currentData.routes = routes;
        renderRouteTable(routes);
    } catch (error) {
        console.error('Error loading routes:', error);
        showError('Failed to load routes');
    } finally {
        hideLoading();
    }
}

function renderRouteTable(routes) {
    const tbody = document.getElementById('route-table-body');
    tbody.innerHTML = routes.map(route => `
        <tr>
            <td>${route.routeName || 'N/A'}</td>
            <td>${route.origin || 'N/A'}</td>
            <td>${route.destination || 'N/A'}</td>
            <td>KES ${route.fare || '0'}</td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-sm btn-secondary" onclick="editRoute(${route.routeId})">
                        <i class="fas fa-edit"></i> Edit
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteRoute(${route.routeId})">
                        <i class="fas fa-trash"></i> Delete
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function showRouteForm(route = null) {
    modalTitle.textContent = route ? 'Edit Route' : 'Add New Route';
    
    form.innerHTML = `
        <div class="form-group">
            <label for="routeName">Route Name</label>
            <input type="text" id="routeName" name="routeName" value="${route?.routeName || ''}" required>
        </div>
        <div class="form-row">
            <div class="form-group">
                <label for="origin">Origin</label>
                <input type="text" id="origin" name="origin" value="${route?.origin || ''}" required>
            </div>
            <div class="form-group">
                <label for="destination">Destination</label>
                <input type="text" id="destination" name="destination" value="${route?.destination || ''}" required>
            </div>
        </div>
        <div class="form-group">
            <label for="fare">Fare (KES)</label>
            <input type="number" id="fare" name="fare" step="0.01" value="${route?.fare || ''}" required>
        </div>
        <div class="form-actions">
            <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancel</button>
            <button type="submit" class="btn btn-primary">
                ${route ? 'Update' : 'Create'} Route
            </button>
        </div>
    `;
    
    form.onsubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());
        data.fare = parseFloat(data.fare);
        
        if (route) {
            updateRoute(route.routeId, data);
        } else {
            createRoute(data);
        }
    };
    
    showModal();
}

async function createRoute(data) {
    showLoading();
    try {
        await fetchData('/route', {
            method: 'POST',
            body: JSON.stringify(data)
        });
        closeModal();
        loadRoutes();
        showSuccess('Route created successfully');
    } catch (error) {
        console.error('Error creating route:', error);
        showError('Failed to create route');
    } finally {
        hideLoading();
    }
}

async function updateRoute(routeId, data) {
    showLoading();
    try {
        await fetchData(`/route/${routeId}`, {
            method: 'POST',
            body: JSON.stringify(data)
        });
        closeModal();
        loadRoutes();
        showSuccess('Route updated successfully');
    } catch (error) {
        console.error('Error updating route:', error);
        showError('Failed to update route');
    } finally {
        hideLoading();
    }
}

async function deleteRoute(routeId) {
    if (!confirm('Are you sure you want to delete this route?')) return;
    
    showLoading();
    try {
        await fetchData(`/route/${routeId}`, { method: 'DELETE' });
        loadRoutes();
        showSuccess('Route deleted successfully');
    } catch (error) {
        console.error('Error deleting route:', error);
        showError('Failed to delete route');
    } finally {
        hideLoading();
    }
}

function editRoute(routeId) {
    const route = currentData.routes.find(r => r.routeId === routeId);
    if (route) {
        showRouteForm(route);
    }
}

// Ticket functionality
async function loadTickets() {
    showLoading();
    try {
        const tickets = await fetchData('/ticket');
        currentData.tickets = tickets;
        renderTicketTable(tickets);
    } catch (error) {
        console.error('Error loading tickets:', error);
        showError('Failed to load tickets');
    } finally {
        hideLoading();
    }
}

function renderTicketTable(tickets) {
    const tbody = document.getElementById('ticket-table-body');
    tbody.innerHTML = tickets.map(ticket => `
        <tr>
            <td>${ticket.seatNumber || 'N/A'}</td>
            <td>${ticket.date || 'N/A'}</td>
            <td>${ticket.passenger?.passengerName || 'N/A'}</td>
            <td>${ticket.route?.routeName || 'N/A'}</td>
            <td>${ticket.matatu?.licenceNumber || 'N/A'}</td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-sm btn-secondary" onclick="editTicket(${ticket.ticketId})">
                        <i class="fas fa-edit"></i> Edit
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteTicket(${ticket.ticketId})">
                        <i class="fas fa-trash"></i> Delete
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function showTicketForm(ticket = null) {
    modalTitle.textContent = ticket ? 'Edit Ticket' : 'Book New Ticket';
    
    form.innerHTML = `
        <div class="form-row">
            <div class="form-group">
                <label for="seatNumber">Seat Number</label>
                <input type="number" id="seatNumber" name="seatNumber" value="${ticket?.seatNumber || ''}" required>
            </div>
            <div class="form-group">
                <label for="date">Date</label>
                <input type="date" id="date" name="date" value="${ticket?.date || ''}" required>
            </div>
        </div>
        <div class="form-group">
            <label for="passengerId">Passenger</label>
            <select id="passengerId" name="passengerId" required>
                <option value="">Select Passenger</option>
                ${currentData.passengers.map(p => `
                    <option value="${p.id}" ${ticket?.passenger?.id === p.id ? 'selected' : ''}>
                        ${p.passengerName}
                    </option>
                `).join('')}
            </select>
        </div>
        <div class="form-group">
            <label for="routeId">Route</label>
            <select id="routeId" name="routeId" required>
                <option value="">Select Route</option>
                ${currentData.routes.map(r => `
                    <option value="${r.routeId}" ${ticket?.route?.routeId === r.routeId ? 'selected' : ''}>
                        ${r.routeName} (${r.origin} - ${r.destination})
                    </option>
                `).join('')}
            </select>
        </div>
        <div class="form-group">
            <label for="matatuId">Matatu</label>
            <select id="matatuId" name="matatuId" required>
                <option value="">Select Matatu</option>
                ${currentData.matatus.map(m => `
                    <option value="${m.licenceNumber}" ${ticket?.matatu?.licenceNumber === m.licenceNumber ? 'selected' : ''}>
                        ${m.licenceNumber} - ${m.driverName}
                    </option>
                `).join('')}
            </select>
        </div>
        <div class="form-actions">
            <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancel</button>
            <button type="submit" class="btn btn-primary">
                ${ticket ? 'Update' : 'Book'} Ticket
            </button>
        </div>
    `;
    
    form.onsubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());
        
        if (ticket) {
            updateTicket(ticket.ticketId, data);
        } else {
            createTicket(data);
        }
    };
    
    showModal();
}

async function createTicket(data) {
    showLoading();
    try {
        await fetchData('/ticket', {
            method: 'POST',
            body: JSON.stringify(data)
        });
        closeModal();
        loadTickets();
        showSuccess('Ticket booked successfully');
    } catch (error) {
        console.error('Error creating ticket:', error);
        showError('Failed to book ticket');
    } finally {
        hideLoading();
    }
}

async function updateTicket(ticketId, data) {
    showLoading();
    try {
        await fetchData(`/ticket/${ticketId}`, {
            method: 'PATCH',
            body: JSON.stringify(data)
        });
        closeModal();
        loadTickets();
        showSuccess('Ticket updated successfully');
    } catch (error) {
        console.error('Error updating ticket:', error);
        showError('Failed to update ticket');
    } finally {
        hideLoading();
    }
}

async function deleteTicket(ticketId) {
    if (!confirm('Are you sure you want to delete this ticket?')) return;
    
    showLoading();
    try {
        await fetchData(`/ticket/${ticketId}`, { method: 'DELETE' });
        loadTickets();
        showSuccess('Ticket deleted successfully');
    } catch (error) {
        console.error('Error deleting ticket:', error);
        showError('Failed to delete ticket');
    } finally {
        hideLoading();
    }
}

function editTicket(ticketId) {
    const ticket = currentData.tickets.find(t => t.ticketId === ticketId);
    if (ticket) {
        showTicketForm(ticket);
    }
}

// Payment functionality
async function loadPayments() {
    showLoading();
    try {
        const payments = await fetchData('/payment');
        currentData.payments = payments;
        renderPaymentTable(payments);
    } catch (error) {
        console.error('Error loading payments:', error);
        showError('Failed to load payments');
    } finally {
        hideLoading();
    }
}

function renderPaymentTable(payments) {
    const tbody = document.getElementById('payment-table-body');
    tbody.innerHTML = payments.map(payment => `
        <tr>
            <td>KES ${payment.amount || '0'}</td>
            <td>${payment.timestamp || 'N/A'}</td>
            <td>${payment.confirmationCode || 'N/A'}</td>
            <td>${payment.passenger?.passengerName || 'N/A'}</td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-sm btn-secondary" onclick="editPayment(${payment.ticketId})">
                        <i class="fas fa-edit"></i> Edit
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deletePayment(${payment.ticketId})">
                        <i class="fas fa-trash"></i> Delete
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function showPaymentForm(payment = null) {
    modalTitle.textContent = payment ? 'Edit Payment' : 'Add New Payment';
    
    form.innerHTML = `
        <div class="form-row">
            <div class="form-group">
                <label for="amount">Amount (KES)</label>
                <input type="number" id="amount" name="amount" step="0.01" value="${payment?.amount || ''}" required>
            </div>
            <div class="form-group">
                <label for="timestamp">Date</label>
                <input type="date" id="timestamp" name="timestamp" value="${payment?.timestamp || ''}" required>
            </div>
        </div>
        <div class="form-group">
            <label for="confirmationCode">Confirmation Code</label>
            <input type="text" id="confirmationCode" name="confirmationCode" value="${payment?.confirmationCode || ''}" required>
        </div>
        <div class="form-group">
            <label for="passengerId">Passenger</label>
            <select id="passengerId" name="passengerId" required>
                <option value="">Select Passenger</option>
                ${currentData.passengers.map(p => `
                    <option value="${p.id}" ${payment?.passenger?.id === p.id ? 'selected' : ''}>
                        ${p.passengerName}
                    </option>
                `).join('')}
            </select>
        </div>
        <div class="form-actions">
            <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancel</button>
            <button type="submit" class="btn btn-primary">
                ${payment ? 'Update' : 'Create'} Payment
            </button>
        </div>
    `;
    
    form.onsubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());
        data.amount = parseFloat(data.amount);
        
        if (payment) {
            updatePayment(payment.ticketId, data);
        } else {
            createPayment(data);
        }
    };
    
    showModal();
}

async function createPayment(data) {
    showLoading();
    try {
        await fetchData('/payment', {
            method: 'POST',
            body: JSON.stringify(data)
        });
        closeModal();
        loadPayments();
        showSuccess('Payment created successfully');
    } catch (error) {
        console.error('Error creating payment:', error);
        showError('Failed to create payment');
    } finally {
        hideLoading();
    }
}

async function updatePayment(ticketId, data) {
    showLoading();
    try {
        await fetchData(`/payment/${ticketId}`, {
            method: 'PATCH',
            body: JSON.stringify(data)
        });
        closeModal();
        loadPayments();
        showSuccess('Payment updated successfully');
    } catch (error) {
        console.error('Error updating payment:', error);
        showError('Failed to update payment');
    } finally {
        hideLoading();
    }
}

async function deletePayment(ticketId) {
    if (!confirm('Are you sure you want to delete this payment?')) return;
    
    showLoading();
    try {
        await fetchData(`/payment/${ticketId}`, { method: 'DELETE' });
        loadPayments();
        showSuccess('Payment deleted successfully');
    } catch (error) {
        console.error('Error deleting payment:', error);
        showError('Failed to delete payment');
    } finally {
        hideLoading();
    }
}

function editPayment(ticketId) {
    const payment = currentData.payments.find(p => p.ticketId === ticketId);
    if (payment) {
        showPaymentForm(payment);
    }
}

// GPS Location functionality
async function loadGpsLocations() {
    showLoading();
    try {
        const gpsLocations = await fetchData('/gps-location');
        currentData.gpsLocations = gpsLocations;
        renderGpsTable(gpsLocations);
    } catch (error) {
        console.error('Error loading GPS locations:', error);
        showError('Failed to load GPS locations');
    } finally {
        hideLoading();
    }
}

function renderGpsTable(gpsLocations) {
    const tbody = document.getElementById('gps-table-body');
    tbody.innerHTML = gpsLocations.map(location => `
        <tr>
            <td>${location.latitude || 'N/A'}</td>
            <td>${location.longitude || 'N/A'}</td>
            <td>${location.timestamp || 'N/A'}</td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-sm btn-danger" onclick="deleteGpsLocation(${location.locationId})">
                        <i class="fas fa-trash"></i> Delete
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function showGpsForm() {
    modalTitle.textContent = 'Add GPS Location';
    
    form.innerHTML = `
        <div class="form-row">
            <div class="form-group">
                <label for="latitude">Latitude</label>
                <input type="number" id="latitude" name="latitude" step="any" required>
            </div>
            <div class="form-group">
                <label for="longitude">Longitude</label>
                <input type="number" id="longitude" name="longitude" step="any" required>
            </div>
        </div>
        <div class="form-group">
            <label for="timestamp">Timestamp</label>
            <input type="datetime-local" id="timestamp" name="timestamp" required>
        </div>
        <div class="form-actions">
            <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancel</button>
            <button type="submit" class="btn btn-primary">Add Location</button>
        </div>
    `;
    
    form.onsubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());
        data.latitude = parseFloat(data.latitude);
        data.longitude = parseFloat(data.longitude);
        
        createGpsLocation(data);
    };
    
    showModal();
}

async function createGpsLocation(data) {
    showLoading();
    try {
        await fetchData('/gps-location', {
            method: 'POST',
            body: JSON.stringify(data)
        });
        closeModal();
        loadGpsLocations();
        showSuccess('GPS location added successfully');
    } catch (error) {
        console.error('Error creating GPS location:', error);
        showError('Failed to add GPS location');
    } finally {
        hideLoading();
    }
}

async function deleteGpsLocation(locationId) {
    if (!confirm('Are you sure you want to delete this GPS location?')) return;
    
    showLoading();
    try {
        await fetchData(`/gps-location/${locationId}`, { method: 'DELETE' });
        loadGpsLocations();
        showSuccess('GPS location deleted successfully');
    } catch (error) {
        console.error('Error deleting GPS location:', error);
        showError('Failed to delete GPS location');
    } finally {
        hideLoading();
    }
}

// Utility functions
async function fetchData(endpoint, options = {}) {
    const url = `${API_BASE_URL}${endpoint}`;
    const config = {
        headers: {
            'Content-Type': 'application/json',
        },
        ...options
    };
    
    // Add authorization header if token exists
    if (authToken) {
        config.headers['Authorization'] = `Bearer ${authToken}`;
    }
    
    const response = await fetch(url, config);
    
    if (!response.ok) {
        if (response.status === 401) {
            // Token expired or invalid
            logout();
            showError('Session expired. Please login again.');
            return null;
        }
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    // Handle empty responses
    const text = await response.text();
    return text ? JSON.parse(text) : null;
}

function showModal() {
    modal.style.display = 'block';
}

function closeModal() {
    modal.style.display = 'none';
    form.innerHTML = '';
}

function showLoading() {
    loading.style.display = 'flex';
}

function hideLoading() {
    loading.style.display = 'none';
}

function showSuccess(message) {
    // Simple success notification - you could enhance this with a proper toast library
    alert(`Success: ${message}`);
}

function showError(message) {
    // Simple error notification - you could enhance this with a proper toast library
    alert(`Error: ${message}`);
}

// Close modal when clicking outside
window.onclick = function(event) {
    if (event.target === modal) {
        closeModal();
    }
}

// Authentication functions
function showLoginForm() {
    modalTitle.textContent = 'Login';
    
    form.innerHTML = `
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-actions">
            <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancel</button>
            <button type="submit" class="btn btn-primary">Login</button>
        </div>
    `;
    
    form.onsubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());
        login(data);
    };
    
    showModal();
}

function showRegisterForm() {
    modalTitle.textContent = 'Register';
    
    form.innerHTML = `
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="passengerName">Full Name</label>
            <input type="text" id="passengerName" name="passengerName" required>
        </div>
        <div class="form-group">
            <label for="passengerEmail">Email</label>
            <input type="email" id="passengerEmail" name="passengerEmail" required>
        </div>
        <div class="form-group">
            <label for="passengerPhone">Phone</label>
            <input type="tel" id="passengerPhone" name="passengerPhone" required>
        </div>
        <div class="form-actions">
            <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancel</button>
            <button type="submit" class="btn btn-primary">Register</button>
        </div>
    `;
    
    form.onsubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());
        register(data);
    };
    
    showModal();
}

async function login(data) {
    showLoading();
    try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });
        
        const result = await response.json();
        
        if (result.token) {
            authToken = result.token;
            currentUser = {
                username: result.username,
                role: result.role
            };
            
            localStorage.setItem('authToken', authToken);
            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            
            closeModal();
            updateUIForUser();
            showSuccess('Login successful');
        } else {
            showError(result.message || 'Login failed');
        }
    } catch (error) {
        console.error('Login error:', error);
        showError('Login failed');
    } finally {
        hideLoading();
    }
}

async function register(data) {
    showLoading();
    try {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });
        
        const result = await response.json();
        
        if (result.token) {
            authToken = result.token;
            currentUser = {
                username: result.username,
                role: result.role
            };
            
            localStorage.setItem('authToken', authToken);
            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            
            closeModal();
            updateUIForUser();
            showSuccess('Registration successful');
        } else {
            showError(result.message || 'Registration failed');
        }
    } catch (error) {
        console.error('Registration error:', error);
        showError('Registration failed');
    } finally {
        hideLoading();
    }
}

function logout() {
    authToken = null;
    currentUser = null;
    localStorage.removeItem('authToken');
    localStorage.removeItem('currentUser');
    updateUIForGuest();
    showSuccess('Logged out successfully');
}

function updateUIForUser() {
    document.getElementById('auth-section').classList.add('hidden');
    document.getElementById('user-section').classList.remove('hidden');
    document.getElementById('username').textContent = currentUser.username;
    
    if (currentUser.role === 'ADMIN') {
        document.querySelectorAll('.admin-only').forEach(el => el.classList.add('show'));
    }
    
    // Load dashboard data
    loadDashboardData();
}

function updateUIForGuest() {
    document.getElementById('auth-section').classList.remove('hidden');
    document.getElementById('user-section').classList.add('hidden');
    document.querySelectorAll('.admin-only').forEach(el => el.classList.remove('show'));
    
    // Clear data
    currentData = {
        matatus: [],
        passengers: [],
        routes: [],
        tickets: [],
        payments: [],
        gpsLocations: []
    };
    
    // Show login/register buttons
    document.querySelectorAll('.section').forEach(section => section.classList.remove('active'));
    document.getElementById('dashboard').classList.add('active');
}

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    // Check if user is already logged in
    if (authToken && localStorage.getItem('currentUser')) {
        currentUser = JSON.parse(localStorage.getItem('currentUser'));
        updateUIForUser();
    } else {
        updateUIForGuest();
    }
}); 