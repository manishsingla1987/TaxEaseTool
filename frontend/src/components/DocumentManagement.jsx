
import React, { useState, useEffect } from 'react';
import axios from 'axios';

// ITR Filing Flow:
// User -> ITR Form: Fill ITR details
// ITR Form -> Backend: POST /api/itr/calculate-tax
// Backend -> ITR Form: Return calculated tax
// User -> ITR Form: Submit ITR
// ITR Form -> Backend: POST /api/itr/submit
// Backend -> Kafka: Publish ITR submission event
// Backend -> ITR Form: Return success
// ITR Form -> Payment: Redirect if tax payable
// Document Management Flow:
// User -> Document Screen: Upload document
// Document Screen -> Backend: POST /api/documents/upload
// Backend -> Kafka: Publish document processing event
// Backend -> Document Screen: Return success
// Document Screen -> Backend: GET /api/documents/user/{userId}
// Backend -> Document Screen: Return document list
// Payment Flow:
// User -> Payment Screen: Initiate payment
// Payment Screen -> Backend: POST /api/payments/initiate
// Backend -> Payment Gateway: Process payment
// Payment Gateway -> Backend: Return status
// Backend -> Kafka: Publish payment status event
// Backend -> Payment Screen: Return payment status


const DocumentManagement = () => {
  const [documents, setDocuments] = useState([]);
  const [documentType, setDocumentType] = useState('');
  const [selectedFile, setSelectedFile] = useState(null);

  useEffect(() => {
    fetchDocuments();
  }, []);

  const fetchDocuments = async () => {
    try {
      const userId = localStorage.getItem('userId');
      const response = await axios.get(`/api/documents/user/${userId}`);
      setDocuments(response.data);
    } catch (error) {
      console.error('Failed to fetch documents:', error);
    }
  };

  const handleUpload = async () => {
    const formData = new FormData();
    formData.append('file', selectedFile);
    formData.append('documentType', documentType);
    formData.append('userId', localStorage.getItem('userId'));

    try {
      await axios.post('/api/documents/upload', formData);
      fetchDocuments();
      alert('Document uploaded successfully');
    } catch (error) {
      alert('Failed to upload document');
    }
  };

  return (
    <div className="document-management">
      <h2>Document Management</h2>
      <div className="upload-section">
        <input 
          type="file" 
          onChange={(e) => setSelectedFile(e.target.files[0])} 
        />
        <select 
          value={documentType}
          onChange={(e) => setDocumentType(e.target.value)}
        >
          <option value="">Select Document Type</option>
          <option value="FORM16">Form 16</option>
          <option value="PANCARD">PAN Card</option>
          <option value="INVESTMENT_PROOF">Investment Proof</option>
        </select>
        <button onClick={handleUpload}>Upload</button>
      </div>

      <div className="documents-list">
        <h3>Your Documents</h3>
        <table>
          <thead>
            <tr>
              <th>Document Type</th>
              <th>Upload Date</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {documents.map(doc => (
              <tr key={doc.id}>
                <td>{doc.documentType}</td>
                <td>{new Date(doc.uploadDate).toLocaleDateString()}</td>
                <td>{doc.status}</td>
                <td>
                  <button onClick={() => window.open(doc.url)}>View</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default DocumentManagement;
