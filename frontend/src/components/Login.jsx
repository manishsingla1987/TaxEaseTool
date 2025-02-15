
import React, { useState } from 'react';
import axios from 'axios';

//User -> Login Screen: Enter PAN and password
// Login Screen -> Backend: POST /api/users/login
// Backend -> Login Screen: Return JWT token
// Login Screen -> Dashboard: Redirect on success


const Login = () => {
  const [credentials, setCredentials] = useState({
    pan: '',
    password: ''
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('/api/users/login', credentials);
      localStorage.setItem('token', response.data.token);
      // Handle successful login
    } catch (error) {
      console.error('Login failed:', error);
    }
  };

  return (
    <div className="login-container">
      <h2>Login to Tax Filing Portal</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="PAN Number"
          value={credentials.pan}
          onChange={(e) => setCredentials({...credentials, pan: e.target.value})}
        />
        <input
          type="password"
          placeholder="Password"
          value={credentials.password}
          onChange={(e) => setCredentials({...credentials, password: e.target.value})}
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default Login;
