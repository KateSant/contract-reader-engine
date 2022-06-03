# How to build and deploy

Hit the GithubAction https://github.com/KateSant/contract-reader-engine/actions/workflows/CD-on-dispatch-deploy-to-prod.yml
It will terraform etc.

# OLD How to build and deploy

##  Build docker container

```
docker build -t gcr.io/contract-reader/contract-reader-image .
docker run -p 80:8080 gcr.io/contract-reader/contract-reader-image (to check)
```

## Push image to Google Container Registry
[One off - created project called contract-reader on Google Cloud 
         - enabled google container registry API https://console.cloud.google.com/gcr/images/contract-reader?project=contract-reader]

```
gcloud auth login  [browser login - oauth - gmail account]
gcloud config set project contract-reader
gcloud auth configure-docker
docker push gcr.io/contract-reader/contract-reader-image
```


## Deploy image to GCP
[One off - create service called contract-reader on Google Cloud Run in region europe-west4 (Netherlands) https://console.cloud.google.com/run?project=contract-reader]   TODO: Terraform this.

```
gcloud run deploy contract-reader --region europe-west4 --project contract-reader --image gcr.io/contract-reader/contract-reader-image --platform managed
```
OR use the UI https://console.cloud.google.com/run?project=contract-reader to pick image from container registry
