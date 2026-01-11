document.getElementById('parseForm').addEventListener('submit', async function (e) {
  e.preventDefault();

  const form = e.target;
  const formData = new FormData(form);

  try {
    const response = await fetch('/parse', {
      method: 'POST',
      body: formData
    });

    if (response.ok) {
      showSuccessPopup();
      form.reset();
      toggleOutputFile();
      return;
    }

    // Error response (expected ApiErrorDto)
    const error = await response.json();
    showErrorPopup(error.message || "An unexpected error occurred.");

  } catch (err) {
    showErrorPopup("Unable to reach server." + err.message);
  }
});

function showSuccessPopup() {
  const popup = document.getElementById('successPopup');
  popup.style.display = 'flex';

  // Auto-hide after 6 seconds
  setTimeout(hideSuccessPopup, 4000);
}

function hideSuccessPopup() {
  const popup = document.getElementById('successPopup');
  popup.style.display = 'none';
}

function showErrorPopup(message) {
  const popup = document.getElementById('errorPopup');
  const messageSpan = document.getElementById('errorMessage');

  messageSpan.textContent = message;
  popup.style.display = 'flex';

  // Auto-hide after 6 seconds
  setTimeout(hideErrorPopup, 6000);
}

function hideErrorPopup() {
  const popup = document.getElementById('errorPopup');
  popup.style.display = 'none';
}

function toggleOutputFile() {
  const target = document.getElementById('outputTarget').value;
  const outputFileGroup = document.getElementById('outputFileGroup');

  if (target === 'file') {
    outputFileGroup.style.display = 'block';
  } else {
    outputFileGroup.style.display = 'none';
  }
}

// Ensure correct state on page reload (e.g. validation errors)
document.addEventListener('DOMContentLoaded', toggleOutputFile);
