
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ItrForm = () => {
  const [formData, setFormData] = useState({
    financialYear: '',
    itrType: '',
    totalIncome: '',
    deductions: '',
    employmentIncome: '',
    otherIncome: '',
    investments: ''
  });

  const [calculatedTax, setCalculatedTax] = useState(null);

  const calculateTax = async () => {
    try {
      const response = await axios.post('/api/itr/calculate-tax', {
        totalIncome: formData.totalIncome
      });
      setCalculatedTax(response.data);
    } catch (error) {
      console.error('Tax calculation failed:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('/api/itr/submit', {
        ...formData,
        userId: localStorage.getItem('userId'),
        status: 'FILED',
        submissionDate: new Date()
      });
      alert('ITR filed successfully!');
      if (response.data.taxPayable > 0) {
        const proceed = window.confirm(`Tax payable: ₹${response.data.taxPayable}. Proceed to payment?`);
        if (proceed) {
          window.location.href = '/payment';
        }
      }
    } catch (error) {
      console.error('ITR filing failed:', error);
      alert('Failed to file ITR');
    }
  };

  return (
    <div className="itr-form">
      <h2>File Your ITR</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Financial Year</label>
          <select
            value={formData.financialYear}
            onChange={(e) => setFormData({...formData, financialYear: e.target.value})}
            required
          >
            <option value="">Select Financial Year</option>
            <option value="2023-24">2023-24</option>
            <option value="2022-23">2022-23</option>
          </select>
        </div>

        <div className="form-group">
          <label>ITR Type</label>
          <select
            value={formData.itrType}
            onChange={(e) => setFormData({...formData, itrType: e.target.value})}
            required
          >
            <option value="">Select ITR Type</option>
            <option value="ITR-1">ITR-1 (Sahaj)</option>
            <option value="ITR-2">ITR-2</option>
            <option value="ITR-3">ITR-3</option>
          </select>
        </div>

        <div className="form-group">
          <label>Employment Income</label>
          <input
            type="number"
            value={formData.employmentIncome}
            onChange={(e) => setFormData({...formData, employmentIncome: e.target.value})}
            required
          />
        </div>

        <div className="form-group">
          <label>Other Income</label>
          <input
            type="number"
            value={formData.otherIncome}
            onChange={(e) => setFormData({...formData, otherIncome: e.target.value})}
          />
        </div>

        <div className="form-group">
          <label>Investments & Deductions</label>
          <input
            type="number"
            value={formData.investments}
            onChange={(e) => setFormData({...formData, investments: e.target.value})}
          />
        </div>

        <button type="button" onClick={calculateTax}>Calculate Tax</button>
        {calculatedTax && (
          <div className="tax-calculation">
            <h3>Tax Calculation</h3>
            <p>Total Tax: ₹{calculatedTax}</p>
          </div>
        )}

        <button type="submit">Submit ITR</button>
      </form>
    </div>
  );
};

export default ItrForm;
