
import React, { useState, useEffect } from 'react';
import axios from 'axios';

// User -> Dashboard: View ITR status
// Dashboard -> Backend: GET /api/itr/status/{userId}
// Backend -> Dashboard: Return ITR details
// Dashboard -> Backend: GET /api/notifications/user/{userId}
// Backend -> Dashboard: Return notifications

const Dashboard = () => {
  const [itrData, setItrData] = useState(null);
  const [documentType, setDocumentType] = useState('');
  const [selectedFile, setSelectedFile] = useState(null);
  const [documents, setDocuments] = useState([]);
  const [notifications, setNotifications] = useState([]);

  const handleFileUpload = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const uploadDocument = async () => {
    if (!selectedFile || !documentType) {
      alert('Please select a file and document type');
      return;
    }

    const formData = new FormData();
    formData.append('file', selectedFile);
    formData.append('userId', localStorage.getItem('userId'));
    formData.append('documentType', documentType);
    formData.append('financialYear', new Date().getFullYear() + '-' + (new Date().getFullYear() + 1));

    try {
      await axios.post('/api/documents/upload', formData);
      alert('Document uploaded successfully');
      fetchDocuments();
    } catch (error) {
      console.error('Upload failed:', error);
      alert('Failed to upload document');
    }
  };

  const fetchDocuments = async () => {
    try {
      const response = await axios.get(`/api/documents/user/${localStorage.getItem('userId')}`);
      setDocuments(response.data);
    } catch (error) {
      console.error('Failed to fetch documents:', error);
    }
  };

  const fetchNotifications = async () => {
    try {
      const response = await axios.get(`/api/notifications/user/${localStorage.getItem('userId')}`);
      setNotifications(response.data);
    } catch (error) {
      console.error('Failed to fetch notifications:', error);
    }
  };

  useEffect(() => {
    fetchDocuments();
    fetchNotifications();
  }, []);

  useEffect(() => {
    const fetchItrData = async () => {
      try {
        const response = await axios.get('/api/itr/status');
        setItrData(response.data);
      } catch (error) {
        console.error('Failed to fetch ITR data:', error);
      }
    };

    fetchItrData();
  }, []);

  return (
    <div className="dashboard">
      <h2>Tax Filing Dashboard</h2>
      {itrData && (
        <div className="itr-status">
          <h3>Current ITR Status</h3>
          <p>Financial Year: {itrData.financialYear}</p>
          <p>Status: {itrData.status}</p>
          <p>Total Income: ₹{itrData.totalIncome}</p>
          <p>Tax Payable: ₹{itrData.taxPayable}</p>
          <p>Last Updated: {new Date(itrData.lastModified).toLocaleDateString()}</p>
        </div>
      )}
      <div className="notifications-panel">
        <h3>Notifications</h3>
        <div className="notification-list">
          {notifications.map(notification => (
            <div key={notification.id} className={`notification-item ${notification.read ? 'read' : 'unread'}`}>
              <span className="notification-message">{notification.message}</span>
              <span className="notification-date">
                {new Date(notification.createdAt).toLocaleDateString()}
              </span>
            </div>
          ))}
        </div>
      </div>
      <div className="quick-actions">
        <button onClick={() => window.location.href='/itr-form'}>File New ITR</button>
        <div className="document-upload">
          <h3>Upload Documents</h3>
          <input type="file" onChange={handleFileUpload} />
          <select onChange={(e) => setDocumentType(e.target.value)}>
            <option value="">Select Document Type</option>
            <option value="FORM16">Form 16</option>
            <option value="PANCARD">PAN Card</option>
            <option value="AADHAAR">Aadhaar Card</option>
          </select>
          <button onClick={uploadDocument}>Upload</button>
        </div>
        {itrData?.taxPayable > 0 && (
          <div className="payment-section">
            <h3>Make Payment</h3>
            <p>Tax Payable: ₹{itrData.taxPayable}</p>
            <button onClick={() => initiatePayment(itrData.taxPayable)}>Pay Now</button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Dashboard;
