// API Base URL
const API_BASE_URL = "https://deployment-latest-dz6y.onrender.com/api";

// DOM Elements
const diseaseContent = document.getElementById('diseaseContent');

// Get disease ID from URL
function getDiseaseIdFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('id');
}

// Fetch and Display Disease
async function loadDisease() {
    const diseaseId = getDiseaseIdFromUrl();
    
    if (!diseaseId) {
        showError('No disease ID provided in the URL');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/diseases/${diseaseId}`);
        
        if (!response.ok) {
            throw new Error('Disease not found');
        }
        
        const disease = await response.json();
        displayDisease(disease);
        
    } catch (error) {
        console.error('Error loading disease:', error);
        showError('Unable to load disease details. Please check if the backend server is running at http://localhost:9090');
    }
}

// Display Disease Details
function displayDisease(disease) {
    const name = disease.diseaseName || disease.name || 'Unknown Disease';
    const generalNotes = disease.generalNotes || '';
    const symptoms = disease.symptoms || [];
    const medicines = disease.medicines || {};
    
    let html = `
        <div class="disease-detail">
            <h1>${escapeHtml(name)}</h1>
            
            ${generalNotes ? `
                <div class="detail-section">
                    <h2>General Notes</h2>
                    <p>${escapeHtml(generalNotes)}</p>
                </div>
            ` : ''}
            
            ${symptoms.length > 0 ? `
                <div class="detail-section">
                    <h2>Symptoms</h2>
                    <ul class="detail-list">
                        ${symptoms.map(symptom => `<li>${escapeHtml(symptom)}</li>`).join('')}
                    </ul>
                </div>
            ` : ''}
            
            ${Object.keys(medicines).length > 0 ? `
                <div class="detail-section">
                    <h2>Medicines</h2>
                    <div class="medicines-detail">
                        ${Object.entries(medicines).map(([medicineName, usage]) => `
                            <div class="medicine-detail-item">
                                <h3>${escapeHtml(medicineName)}</h3>
                                ${usage ? `<p class="usage-text">${escapeHtml(usage)}</p>` : '<p class="usage-text" style="color: #999;">No usage notes provided</p>'}
                            </div>
                        `).join('')}
                    </div>
                </div>
            ` : ''}
        </div>
    `;
    
    diseaseContent.innerHTML = html;
}

// Show Error Message
function showError(message) {
    diseaseContent.innerHTML = `
        <div class="message-container active error">
            ❌ ${escapeHtml(message)}
        </div>
        <div style="text-align: center; margin-top: 20px;">
            <a href="search.html" class="btn btn-primary" style="display: inline-block; width: auto; text-decoration: none;">← Back to Search</a>
        </div>
    `;
}

// Utility: Escape HTML
function escapeHtml(text) {
    if (typeof text !== 'string') {
        return text;
    }
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Load disease on page load
loadDisease();

console.log('Disease detail page loaded successfully!');
