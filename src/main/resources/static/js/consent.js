// Consent page JavaScript for OAuth2 Authorization
document.addEventListener('DOMContentLoaded', function() {
  const authorizeForms = document.querySelectorAll('form[action*="/oauth2/authorize"]');
  const authorizeBtn = document.querySelector('button[value="approve"]');
  const denyBtn = document.querySelector('button[value="deny"]');
  const permissionItems = document.querySelectorAll('.permission-item');

  function showButtonLoading(button, isLoading) {
    if (isLoading) {
      button.disabled = true;
      button.style.opacity = '0.7';
      button.textContent = button.value === 'approve' ? 'Authorizing...' : 'Denying...';
    } else {
      button.disabled = false;
      button.style.opacity = '1';
      button.textContent = button.value === 'approve' ? 'Authorize' : 'Deny';
    }
  }

  authorizeForms.forEach(form => {
    form.addEventListener('submit', function(e) {
      const submitButton = form.querySelector('button[type="submit"]');
      const isApproval = submitButton.value === 'approve';

      if (!isApproval) {
        e.preventDefault();

        if (confirm('Are you sure you want to deny access to this application?')) {
          showButtonLoading(submitButton, true);
          form.submit();
        }
        return;
      }

      showButtonLoading(submitButton, true);

    });
  });

  permissionItems.forEach(item => {
    const checkbox = item.querySelector('input[type="checkbox"]');
    const label = item.querySelector('label');

    item.style.padding = '0.5rem';
    item.style.borderRadius = '0.375rem';
    item.style.backgroundColor = '#f8f9fa';
    item.style.border = '1px solid #dee2e6';
    item.style.marginBottom = '0.5rem';

    item.addEventListener('mouseenter', function() {
      if (!checkbox.disabled) {
        this.style.backgroundColor = '#e9ecef';
      }
    });

    item.addEventListener('mouseleave', function() {
      this.style.backgroundColor = '#f8f9fa';
    });
  });

  document.addEventListener('keydown', function(e) {
    if (e.key === 'Enter') {
      if (document.activeElement === authorizeBtn) {
        e.preventDefault();
        authorizeBtn.closest('form').dispatchEvent(new Event('submit'));
      }
      else if (document.activeElement === denyBtn) {
        e.preventDefault();
        denyBtn.closest('form').dispatchEvent(new Event('submit'));
      }
    }

    if (e.key === 'Escape') {
      denyBtn.focus();
    }
  });

  if (authorizeBtn) {
    authorizeBtn.focus();
  }

  const sensitiveScopes = ['read', 'write', 'admin', 'delete'];
  const requestedScopes = Array.from(document.querySelectorAll('input[name="scope"]'))
    .map(input => input.value.toLowerCase());

  const hasSensitiveScopes = sensitiveScopes.some(scope =>
    requestedScopes.some(requested => requested.includes(scope))
  );

  if (hasSensitiveScopes) {
    const warningDiv = document.createElement('div');
    warningDiv.className = 'security-warning';
    warningDiv.style.cssText = `
            background-color: #fff3cd;
            border: 1px solid #ffeaa7;
            color: #856404;
            padding: 0.75rem;
            border-radius: 0.375rem;
            margin: 1rem 0;
            font-size: 0.875rem;
        `;
    warningDiv.innerHTML = `
            <div style="display: flex; align-items: center;">
                <span style="font-weight: bold; margin-right: 0.5rem;">⚠️</span>
                <span>This application is requesting sensitive permissions. Please ensure you trust this application before proceeding.</span>
            </div>
        `;

    const permissionsSection = document.querySelector('.permissions-section');
    if (permissionsSection) {
      permissionsSection.appendChild(warningDiv);
    }
  }

  function trackConsentDecision(decision) {
    console.log('Consent decision:', decision);


  }

  if (authorizeBtn) {
    authorizeBtn.addEventListener('click', () => trackConsentDecision('approved'));
  }

  if (denyBtn) {
    denyBtn.addEventListener('click', () => trackConsentDecision('denied'));
  }

  let timeoutWarning;
  const TIMEOUT_WARNING_TIME = 10 * 60 * 1000; // 10 minutes

  function showTimeoutWarning() {
    if (timeoutWarning) return;

    timeoutWarning = document.createElement('div');
    timeoutWarning.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
            padding: 1rem;
            border-radius: 0.375rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            z-index: 1000;
            max-width: 300px;
        `;
    timeoutWarning.innerHTML = `
            <div style="font-weight: bold; margin-bottom: 0.5rem;">Session Timeout Warning</div>
            <div style="font-size: 0.875rem;">Your session will expire soon. Please make a decision to continue.</div>
            <button onclick="this.parentElement.remove()" style="
                background: none;
                border: none;
                color: #721c24;
                font-size: 1.2rem;
                position: absolute;
                top: 0.5rem;
                right: 0.5rem;
                cursor: pointer;
            ">&times;</button>
        `;

    document.body.appendChild(timeoutWarning);
  }

});
