# local running
gcloud config set project contract-reader
gcloud auth login

And give intellij an env var:
GOOGLE_APPLICATION_CREDENTIALS=/Users/kate/gcp-creds.json

# How to build and deploy
Hit the GithubAction https://github.com/KateSant/contract-reader-engine/actions/workflows/CD-on-dispatch-deploy-to-prod.yml
It will terraform etc.

