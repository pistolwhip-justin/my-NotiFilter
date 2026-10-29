# NotiFilter diagnostic report upload

See [README-token-setup.txt](README-token-setup.txt) for phone setup and token permissions. The included `github_token.txt` is a placeholder template only. Never commit a real token.

The app uploads only whitelisted service lifecycle and filter/database initialization events to `diagnostics/submissions/` on the diagnostic branch. Reports are public because this repository is public. They exclude notification content and the authorization token.
