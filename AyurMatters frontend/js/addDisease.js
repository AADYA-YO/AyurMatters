// API Configuration
const API_BASE_URL = "https://deployment-latest-dz6y.onrender.com/api";

// Arrays to store symptoms and medicines
let symptoms = [];
let medicines = [];

// Submission guard to prevent double submits
let isSubmitting = false;

// DOM Elements
const diseaseForm = document.getElementById('diseaseForm');
const symptomInput = document.getElementById('symptomInput');
const addSymptomBtn = document.getElementById('addSymptomBtn');
const symptomsList = document.getElementById('symptomsList');

const medicineNameInput = document.getElementById('medicineNameInput');
const medicineUsageInput = document.getElementById('medicineUsageInput');
const addMedicineBtn = document.getElementById('addMedicineBtn');
const medicinesList = document.getElementById('medicinesList');

const formMessage = document.getElementById('formMessage');

// Add Symptom
addSymptomBtn.addEventListener('click', () => {
    const symptom = normalizeText(symptomInput.value);
    
    if (!symptom) {
        showMessage('⚠️ Please enter a symptom before adding', 'error');
        symptomInput.focus();
        return;
    }
    
    // Check for duplicate (case-insensitive)
    const isDuplicate = symptoms.some(s => s.toLowerCase() === symptom.toLowerCase());
    if (isDuplicate) {
        showMessage('⚠️ This symptom has already been added', 'error');
        symptomInput.focus();
        return;
    }
    
    symptoms.push(symptom);
    symptomInput.value = '';
    renderSymptoms();
    showMessage('✓ Symptom added successfully', 'success');
    setTimeout(() => formMessage.classList.remove('active'), 2000);
});

// Allow Enter key to add symptom
symptomInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        e.preventDefault();
        addSymptomBtn.click();
    }
});

// Render Symptoms as Tags
function renderSymptoms() {
    if (symptoms.length === 0) {
        symptomsList.innerHTML = '<p style="color: #999; font-size: 18px;">No symptoms added yet</p>';
        return;
    }
    
    symptomsList.innerHTML = symptoms.map((symptom, index) => `
        <div class="tag">
            ${escapeHtml(symptom)}
            <button class="remove-btn" onclick="removeSymptom(${index})" type="button">×</button>
        </div>
    `).join('');
}

// Remove Symptom
function removeSymptom(index) {
    symptoms.splice(index, 1);
    renderSymptoms();
}

// Add Medicine
addMedicineBtn.addEventListener('click', () => {
    const medicineName = normalizeText(medicineNameInput.value);
    const medicineUsage = normalizeText(medicineUsageInput.value);
    
    if (!medicineName) {
        showMessage('⚠️ Please enter a medicine name before adding', 'error');
        return;
    }
    
    // Check for duplicate medicine
    const isDuplicate = medicines.some(med => med.name.toLowerCase() === medicineName.toLowerCase());
    if (isDuplicate) {
        showMessage('⚠️ This medicine has already been added', 'error');
        medicineNameInput.focus();
        return;
    }
    
    medicines.push({
        name: medicineName,
        usage: medicineUsage
    });
    
    medicineNameInput.value = '';
    medicineUsageInput.value = '';
    renderMedicines();
    showMessage('✓ Medicine added successfully', 'success');
    setTimeout(() => formMessage.classList.remove('active'), 2000);
});

// Allow Enter key in medicine name to add medicine
medicineNameInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        e.preventDefault();
        addMedicineBtn.click();
    }
});

// Render Medicines List
function renderMedicines() {
    if (medicines.length === 0) {
        medicinesList.innerHTML = '<p style="color: #999; font-size: 18px;">No medicines added yet</p>';
        return;
    }
    
    medicinesList.innerHTML = medicines.map((medicine, index) => `
        <div class="medicine-row">
            <div class="medicine-row-header">
                <div class="medicine-name">${escapeHtml(medicine.name)}</div>
                <button class="remove-btn" onclick="removeMedicine(${index})" type="button">Remove</button>
            </div>
            ${medicine.usage ? `<div class="medicine-usage">${escapeHtml(medicine.usage)}</div>` : ''}
        </div>
    `).join('');
}

// Remove Medicine
function removeMedicine(index) {
    medicines.splice(index, 1);
    renderMedicines();
}

// Form Submission
diseaseForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    // Guard against double submission
    if (isSubmitting) {
        return;
    }
    
    const diseaseName = normalizeText(document.getElementById('diseaseName').value);
    const generalNotes = normalizeText(document.getElementById('generalNotes').value);
    
    if (!diseaseName) {
        showMessage('⚠️ Disease name is required. Please enter a name.', 'error');
        document.getElementById('diseaseName').focus();
        return;
    }
    
    if (symptoms.length === 0 && medicines.length === 0) {
        showMessage('⚠️ Please add at least one symptom or medicine', 'error');
        return;
    }
    
    // Build medicines object (key-value pairs)
    const medicinesObject = {};
    medicines.forEach(med => {
        medicinesObject[med.name] = med.usage || '';
    });
    
    const diseaseData = {
        diseaseName: diseaseName,
        generalNotes: generalNotes,
        symptoms: symptoms,
        medicines: medicinesObject
    };
    
    // Disable submit button and set guard
    const submitBtn = diseaseForm.querySelector('button[type="submit"]');
    isSubmitting = true;
    submitBtn.disabled = true;
    submitBtn.textContent = 'Saving...';
    
    try {
        const response = await fetch(`${API_BASE_URL}/diseases`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(diseaseData)
        });
        
        if (!response.ok) {
            // Try to get error message from backend
            let errorMessage = 'Failed to save disease';
            try {
                const errorData = await response.json();
                if (errorData.message || errorData.error) {
                    errorMessage = errorData.message || errorData.error;
                }
            } catch (parseError) {
                // If parsing fails, use default message
            }
            throw new Error(errorMessage);
        }
        
        const result = await response.json();
        console.log('Disease saved:', result);
        showMessage('✅ Success! Disease record has been saved.', 'success');
        
        // Reset form after 2 seconds
        setTimeout(() => {
            resetForm();
        }, 2000);
        
    } catch (error) {
        console.error('Error saving disease:', error);
        const errorMsg = error.message || 'Unable to save disease';
        showMessage(`❌ ${errorMsg}. Please check if the backend server is running at ${API_BASE_URL.replace('/api', '')}`, 'error');
    } finally {
        // Re-enable submit button and clear guard
        isSubmitting = false;
        submitBtn.disabled = false;
        submitBtn.textContent = 'Save Disease';
    }
});

// Reset Form
function resetForm() {
    diseaseForm.reset();
    symptoms = [];
    medicines = [];
    renderSymptoms();
    renderMedicines();
    formMessage.classList.remove('active');
}

// Show Message
function showMessage(message, type) {
    formMessage.className = `message-container active ${type}`;
    formMessage.textContent = message;
    
    setTimeout(() => {
        formMessage.classList.remove('active');
    }, 5000);
}

// Utility: Normalize text input (trim and collapse multiple spaces)
function normalizeText(text) {
    if (typeof text !== 'string') {
        return text;
    }
    return text.trim().replace(/\s+/g, ' ');
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

// Keyboard shortcut: Ctrl+Enter or Cmd+Enter to submit form
document.addEventListener('keydown', (e) => {
    if ((e.ctrlKey || e.metaKey) && e.key === 'Enter') {
        // Check if we're focused on a form element within the disease form
        const activeElement = document.activeElement;
        if (diseaseForm.contains(activeElement)) {
            e.preventDefault();
            diseaseForm.requestSubmit();
        }
    }
});

// Initialize
renderSymptoms();
renderMedicines();

console.log('Add Disease form loaded successfully!');

