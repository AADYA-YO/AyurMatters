// API Base URL
const API_BASE_URL = "https://deployment-latest-dz6y.onrender.com/api";
// DOM Elements
const searchBtn = document.getElementById('searchBtn');
const searchInput = document.getElementById('searchInput');
const searchResults = document.getElementById('searchResults');
const searchTypeRadios = document.querySelectorAll('input[name="searchType"]');

// Get selected search type
function getSearchType() {
    const selected = document.querySelector('input[name="searchType"]:checked');
    return selected ? selected.value : 'disease';
}

// Search Button Click
searchBtn.addEventListener('click', async () => {
    const query = searchInput.value.trim();
    const type = getSearchType();
    
    if (!query) {
        showMessage('⚠️ Please enter a search term to begin searching', 'error');
        searchInput.focus();
        return;
    }
    
    if (query.length < 2) {
        showMessage('⚠️ Please enter at least 2 characters to search', 'error');
        searchInput.focus();
        return;
    }
    
    await performSearch(type, query);
});

// Allow Enter key to search
searchInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        searchBtn.click();
    }
});

// Perform Search
async function performSearch(type, query) {
    showLoading();
    
    // Disable search button
    searchBtn.disabled = true;
    searchBtn.textContent = 'Searching...';
    
    try {
        const response = await fetch(`${API_BASE_URL}/diseases/search?type=${type}&q=${encodeURIComponent(query)}`);
        
        if (!response.ok) {
            throw new Error('Search failed');
        }
        
        const results = await response.json();
        displayResults(results);
        
    } catch (error) {
        console.error('Search error:', error);
        showMessage('❌ Search failed. Please check if the backend server is running at http://localhost:9090', 'error');
    } finally {
        // Re-enable search button
        searchBtn.disabled = false;
        searchBtn.textContent = 'Search';
    }
}

// Display Search Results
function displayResults(results) {
    searchResults.classList.add('active');
    
    if (!results || results.length === 0) {
        searchResults.innerHTML = '<div class="empty-state">🔍 No results found for "' + escapeHtml(searchInput.value) + '". Try a different search term or search type.</div>';
        return;
    }
    
    let html = '<div style="margin-bottom: 15px;"><strong>Found ' + results.length + ' result(s):</strong></div>';
    
    results.forEach(result => {
        const id = result.id || result._id || '';
        const name = result.diseaseName || result.name || 'Unknown';
        const symptoms = result.symptoms || [];
        const medicines = result.medicines || {};
        
        html += `
            <div class="result-item" onclick="viewDisease('${escapeHtml(id)}')">
                <h3>${escapeHtml(name)}</h3>
                ${symptoms.length > 0 ? `<p><strong>Symptoms:</strong> ${escapeHtml(symptoms.slice(0, 3).join(', '))}${symptoms.length > 3 ? '...' : ''}</p>` : ''}
                ${Object.keys(medicines).length > 0 ? `<p><strong>Medicines:</strong> ${escapeHtml(Object.keys(medicines).slice(0, 3).join(', '))}${Object.keys(medicines).length > 3 ? '...' : ''}</p>` : ''}
                <p style="font-size: 16px; color: #81c784; margin-top: 10px;">Click to view full details →</p>
            </div>
        `;
    });
    
    searchResults.innerHTML = html;
}

// Navigate to disease detail page
function viewDisease(id) {
    if (id) {
        window.location.href = `disease.html?id=${encodeURIComponent(id)}`;
    }
}

// Show Loading State
function showLoading() {
    searchResults.classList.add('active');
    searchResults.innerHTML = '<div class="loading">Searching</div>';
}

// Show Message
function showMessage(message, type) {
    searchResults.classList.add('active');
    searchResults.innerHTML = `<div class="message-container active ${type}">${escapeHtml(message)}</div>`;
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

console.log('Search page loaded successfully!');
