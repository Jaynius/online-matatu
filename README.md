# Online Matatu Management System - Frontend

A modern, responsive web frontend for the Online Matatu Management System API. This frontend provides a comprehensive interface for managing matatus (public transport vehicles), passengers, routes, tickets, payments, and GPS tracking.

## 🚀 Features

### Authentication & Authorization
- **User Registration**: Passengers can register with username, password, and personal details
- **User Login**: Secure login with JWT token authentication
- **Role-Based Access**: Admin and Passenger roles with different permissions
- **Session Management**: Persistent login sessions with token storage

### Dashboard
- **Real-time Statistics**: View total matatus, passengers, routes, and tickets
- **Recent Activity Feed**: Track system activities and updates
- **Visual Cards**: Beautiful statistics cards with hover effects

### Matatu Management (Admin Only)
- **CRUD Operations**: Create, Read, Update, Delete matatus
- **Driver Information**: Manage driver names, license numbers, and capacity
- **Route Assignment**: Associate matatus with specific routes
- **Search & Filter**: Easy data management and navigation

### Passenger Management (Admin Only)
- **Passenger Registration**: Add new passengers with contact details
- **Profile Management**: Update passenger information
- **Contact Details**: Email and phone number management

### Route Management (Admin Only)
- **Route Creation**: Define transport routes with origin and destination
- **Fare Management**: Set and update route fares
- **Route Information**: Complete route details and statistics

### Ticket Booking
- **Seat Management**: Book specific seats on matatus
- **Date Selection**: Choose travel dates
- **Passenger Association**: Link tickets to registered passengers
- **Route & Matatu Selection**: Comprehensive booking options
- **Personal Tickets**: Passengers can only view their own tickets

### Payment Processing
- **Payment Records**: Track all payment transactions
- **Confirmation Codes**: Generate and manage payment confirmations
- **Amount Tracking**: Monitor payment amounts and timestamps
- **Personal Payments**: Passengers can only view their own payments

### GPS Tracking (Admin Only)
- **Location Management**: Add and track GPS coordinates
- **Timestamp Recording**: Track location data with timestamps
- **Map Integration Ready**: Prepared for map visualization

## 🎨 Design Features

- **Modern UI/UX**: Clean, professional design with glassmorphism effects
- **Responsive Design**: Works perfectly on desktop, tablet, and mobile devices
- **Interactive Elements**: Hover effects, animations, and smooth transitions
- **Color Scheme**: Professional blue gradient theme
- **Typography**: Modern Inter font family
- **Icons**: Font Awesome icons throughout the interface

## 🛠️ Technology Stack

- **HTML5**: Semantic markup and structure
- **CSS3**: Modern styling with Flexbox, Grid, and animations
- **JavaScript (ES6+)**: Vanilla JavaScript with async/await
- **Font Awesome**: Icon library
- **Google Fonts**: Inter font family

## 📋 Prerequisites

Before running this frontend, ensure you have:

1. **Spring Boot Backend**: Your Java Spring Boot application running
2. **CORS Configuration**: Backend should allow CORS requests from the frontend
3. **Modern Browser**: Chrome, Firefox, Safari, or Edge (latest versions)

## 🚀 Quick Start

### 1. Backend Setup

Ensure your Spring Boot application is running on `http://localhost:8080` with the following endpoints:

**Authentication Endpoints:**
```
POST   /api/v1/auth/register
POST   /api/v1/auth/login
```

**Admin-Only Endpoints:**
```
GET    /api/v1/matatu
POST   /api/v1/matatu
GET    /api/v1/matatu/{licenceNumber}
PATCH  /api/v1/matatu/{licenceNumber}
DELETE /api/v1/matatu/{licenceNumber}

GET    /api/v1/passenger
POST   /api/v1/passenger
GET    /api/v1/passenger/{id}
PATCH  /api/v1/passenger/{id}
DELETE /api/v1/passenger/{id}

GET    /api/v1/route
POST   /api/v1/route
GET    /api/v1/route/{routeId}
POST   /api/v1/route/{routeId}
DELETE /api/v1/route/{routeId}

GET    /api/v1/gps-location
POST   /api/v1/gps-location
DELETE /api/v1/gps-location/{locationId}
```

**Authenticated User Endpoints:**
```
GET    /api/v1/ticket
POST   /api/v1/ticket
GET    /api/v1/ticket/{ticketId}
PATCH  /api/v1/ticket/{ticketId}
DELETE /api/v1/ticket/{ticketId}

GET    /api/v1/payment
POST   /api/v1/payment
GET    /api/v1/payment/{ticketId}
PATCH  /api/v1/payment/{ticketId}
DELETE /api/v1/payment/{ticketId}
```



### 2. CORS Configuration

Add this configuration to your Spring Boot application:

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000", "http://127.0.0.1:3000", "file://")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

### 3. Frontend Setup

1. **Download Files**: Ensure you have all three files in the same directory:
   - `index.html`
   - `styles.css`
   - `script.js`

2. **Open Frontend**: 
   - **Option A**: Double-click `index.html` to open in your browser
   - **Option B**: Use a local server (recommended):
     ```bash
     # Using Python 3
     python -m http.server 3000
     
     # Using Node.js (if you have http-server installed)
     npx http-server -p 3000
     
     # Using PHP
     php -S localhost:3000
     ```

3. **Access Application**: Open your browser and navigate to:
   - If using file://: `file:///path/to/your/index.html`
   - If using local server: `http://localhost:3000`

## 🔧 Configuration

### API Base URL

If your Spring Boot application runs on a different port or host, update the API base URL in `script.js`:

```javascript
const API_BASE_URL = 'http://localhost:8080/api/v1';
```

Change `8080` to your actual port number.

## 📱 Usage Guide

### Authentication
- **Register**: New passengers can create accounts with username, password, and personal details
- **Login**: Users can login with their credentials
- **Logout**: Users can logout to end their session
- **Session Persistence**: Login sessions are maintained across browser sessions

### Dashboard
- View system overview and statistics
- Monitor recent activities
- Quick access to all sections

### Adding Data
1. Click the "Add" button in any section
2. Fill in the required information
3. Click "Create" to save

### Editing Data
1. Click the "Edit" button next to any item
2. Modify the information
3. Click "Update" to save changes

### Deleting Data
1. Click the "Delete" button next to any item
2. Confirm the deletion in the popup
3. Data will be permanently removed

### Navigation
- Use the navigation buttons to switch between sections
- Each section loads its data automatically
- Active section is highlighted
- **Admin Features**: Only visible to admin users (matatus, passengers, routes, GPS tracking)
- **Passenger Features**: Available to all authenticated users (tickets, payments)

## 🎯 API Integration

The frontend integrates with your Spring Boot API using:

- **RESTful API calls**: Standard HTTP methods (GET, POST, PATCH, DELETE)
- **JSON Data Format**: All requests and responses use JSON
- **Error Handling**: Comprehensive error handling and user feedback
- **Loading States**: Visual feedback during API operations

## 🔒 Security Considerations

- **JWT Authentication**: Secure token-based authentication
- **Password Hashing**: Passwords are securely hashed using BCrypt
- **Role-Based Access Control**: Different permissions for admin and passenger roles
- **CORS**: Ensure proper CORS configuration on your backend
- **Input Validation**: Frontend includes basic validation
- **Error Handling**: Graceful error handling and user feedback
- **Data Sanitization**: Basic input sanitization implemented
- **Session Management**: Secure token storage and automatic logout on expiration

## 🚀 Future Enhancements

### Potential Improvements
- **Real-time Updates**: WebSocket integration for live data
- **Advanced Filtering**: Search and filter capabilities
- **Data Export**: Export data to CSV/PDF
- **User Authentication**: Login/logout functionality
- **Role-based Access**: Different user roles and permissions
- **Map Integration**: Real GPS tracking with maps
- **Notifications**: Toast notifications instead of alerts
- **Offline Support**: Service worker for offline functionality

### Map Integration
To add real map functionality, you could integrate:
- **Google Maps API**
- **Leaflet.js**
- **Mapbox**
- **OpenStreetMap**

## 🐛 Troubleshooting

### Common Issues

1. **CORS Errors**
   - Ensure your Spring Boot application has CORS configured
   - Check that the API base URL is correct

2. **API Connection Issues**
   - Verify your Spring Boot application is running
   - Check the console for error messages
   - Ensure the API endpoints match your backend

3. **Data Not Loading**
   - Check browser console for JavaScript errors
   - Verify API responses in Network tab
   - Ensure your backend returns proper JSON

4. **Styling Issues**
   - Clear browser cache
   - Ensure all CSS files are loaded
   - Check for CSS conflicts

### Debug Mode

Open browser developer tools (F12) to:
- View console logs for errors
- Monitor network requests
- Debug JavaScript issues
- Inspect HTML structure

## 📄 License

This frontend is provided as-is for educational and development purposes.

## 🤝 Contributing

Feel free to enhance this frontend by:
- Adding new features
- Improving the UI/UX
- Fixing bugs
- Adding better error handling
- Implementing additional functionality

## 📞 Support

If you encounter any issues:
1. Check the troubleshooting section
2. Review browser console for errors
3. Verify API connectivity
4. Ensure all files are in the same directory

---

**Happy Coding! 🚀** 