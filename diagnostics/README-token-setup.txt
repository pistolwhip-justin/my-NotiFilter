NOTIFILTER DIAGNOSTIC TOKEN SETUP (Samsung A15)

1. Create a fine-grained personal access token while signed into your own GitHub account:
   https://github.com/settings/personal-access-tokens/new
2. Token name: NotiFilter Diagnostic Upload
   Expiration: choose a short period suitable for testing.
   Resource owner: pistolwhip-justin
   Repository access: Only select repositories -> my-NotiFilter
   Repository permissions: Contents -> Read and write. No other permissions are needed.
3. Generate the token and copy it immediately. GitHub will not show the full token again.
4. On your phone, save github_token.txt to Downloads. Open it in a plain-text editor, replace the entire placeholder line with the token, and save. The file must contain ONLY the token, with no quotes, labels, spaces, or extra lines.
   IMPORTANT: Never commit the edited file, upload it to GitHub, or send it in chat. Keep it on your phone.
5. Install NotiFilter_DiagnosticWTF.apk. Open Settings -> Diagnostic report -> Import token file, select Downloads/github_token.txt. The app encrypts the token with Android Keystore and stores it privately. You can then delete the plaintext file from Downloads.
6. Reproduce the problem or wait until the listener/filter issue occurs. Open Settings -> Diagnostic report -> Upload diagnostics.
7. Reports are uploaded to the PUBLIC repository:
   https://github.com/pistolwhip-justin/my-NotiFilter/tree/diagnostic/samsung-a15-listener-persistence/diagnostics/submissions
   Reports contain app version, Android SDK level, timestamps, and whitelisted service/database event details only. They do not include notification text, notification contents, installed-app lists, device IDs, or the token.
8. After testing, revoke the token at https://github.com/settings/personal-access-tokens and use Settings -> Diagnostic report -> Forget token if desired.

SECURITY
- GitHub Actions cannot create a personal access token for your account. Token generation must be authorized in GitHub's account interface.
- Never paste the token into a GitHub Action input, source code, issue, or public repository.
- The token file in this repo is a TEMPLATE only. It contains no real token. Edit a copy on your phone; do not commit the edited copy.
