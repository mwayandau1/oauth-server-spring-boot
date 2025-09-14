document.addEventListener('DOMContentLoaded', function() {
  const loginForm = document.getElementById('login-form');
  const usernameInput = document.getElementById('username');
  const passwordInput = document.getElementById('password-field');
  const togglePasswordBtn = document.getElementById('toggle-password');
  const passwordToggleIcon = document.getElementById('password-toggle-icon');
  const submitBtn = document.getElementById('submit-btn');
  const btnText = document.getElementById('btn-text');
  const loadingSpinner = document.getElementById('loading-spinner');
  const rememberMeCheckbox = document.getElementById('remember-me');
  const forgotPasswordLink = document.getElementById('forgot-password-link');

  let isPasswordVisible = false;

  togglePasswordBtn.addEventListener('click', function(e) {
    e.preventDefault();

    if (isPasswordVisible) {
      passwordInput.type = 'password';
      passwordToggleIcon.src = '/images/eyes-closed.svg';
      passwordToggleIcon.alt = 'closed-eye-icon';
      isPasswordVisible = false;
    } else {
      passwordInput.type = 'text';
      passwordToggleIcon.src = '/images/eye.svg';
      passwordToggleIcon.alt = 'open-eye-icon';
      isPasswordVisible = true;
    }
  });

  function validateForm() {
    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();

    clearErrorStyles();

    let isValid = true;

    if (!username) {
      showFieldError(usernameInput, 'Email is required');
      isValid = false;
    } else if (!isValidEmail(username)) {
      showFieldError(usernameInput, 'Please enter a valid email address');
      isValid = false;
    }

    if (!password) {
      showFieldError(passwordInput, 'Password is required');
      isValid = false;
    } else if (password.length < 6) {
      showFieldError(passwordInput, 'Password must be at least 6 characters');
      isValid = false;
    }

    return isValid;
  }

  // function isValidEmail(email) {
  //   const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  //   return emailRegex.test(email);
  // }

  function showFieldError(field, message) {
    field.style.borderColor = '#dc3545';
    field.style.backgroundColor = '#fff5f5';

    const existingError = field.parentNode.querySelector('.field-error');
    if (existingError) {
      existingError.remove();
    }

    const errorElement = document.createElement('div');
    errorElement.className = 'field-error';
    errorElement.style.color = '#dc3545';
    errorElement.style.fontSize = '0.875rem';
    errorElement.style.marginTop = '0.25rem';
    errorElement.textContent = message;

    field.parentNode.appendChild(errorElement);
  }

  function clearErrorStyles() {
    const inputs = [usernameInput, passwordInput];
    inputs.forEach(input => {
      input.style.borderColor = '';
      input.style.backgroundColor = '';

      const errorElement = input.parentNode.querySelector('.field-error');
      if (errorElement) {
        errorElement.remove();
      }
    });
  }

  function showLoading(show) {
    if (show) {
      submitBtn.disabled = true;
      btnText.style.display = 'none';
      loadingSpinner.style.display = 'inline';
      submitBtn.style.opacity = '0.7';
    } else {
      submitBtn.disabled = false;
      btnText.style.display = 'inline';
      loadingSpinner.style.display = 'none';
      submitBtn.style.opacity = '1';
    }
  }

  loginForm.addEventListener('submit', function(e) {


    if (!validateForm()) {
      return;
    }

    showLoading(true);


  });

  function showLoginError(message) {
    const existingError = document.querySelector('.error-message');
    if (existingError) {
      existingError.remove();
    }

    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.style.cssText = 'color: #dc3545; margin-bottom: 1rem; padding: 0.75rem; background-color: #f8d7da; border: 1px solid #f5c6cb; border-radius: 0.375rem;';
    errorDiv.innerHTML = `<span>${message}</span>`;

    const formContainer = document.querySelector('.form-content');
    formContainer.parentNode.insertBefore(errorDiv, formContainer);

    setTimeout(() => {
      if (errorDiv.parentNode) {
        errorDiv.remove();
      }
    }, 5000);
  }

  function loadRememberedUsername() {
    const rememberedUsername = localStorage.getItem('rememberedUsername');
    if (rememberedUsername) {
      usernameInput.value = rememberedUsername;
      rememberMeCheckbox.checked = true;
    }
  }

  function saveRememberedUsername() {
    if (rememberMeCheckbox.checked) {
      localStorage.setItem('rememberedUsername', usernameInput.value);
    } else {
      localStorage.removeItem('rememberedUsername');
    }
  }

  loadRememberedUsername();


  usernameInput.addEventListener('input', function() {
    if (this.value.trim()) {
      clearFieldError(this);
    }
  });

  passwordInput.addEventListener('input', function() {
    if (this.value.trim()) {
      clearFieldError(this);
    }
  });

  function clearFieldError(field) {
    field.style.borderColor = '';
    field.style.backgroundColor = '';

    const errorElement = field.parentNode.querySelector('.field-error');
    if (errorElement) {
      errorElement.remove();
    }
  }

  if (!usernameInput.value.trim()) {
    usernameInput.focus();
  }
});
