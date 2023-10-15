# What does this application do?

This application extracts key information from legal contracts.  For example the obligations, parties and duration.  

You can try it out by uploading a word document at: https://api.contracthub.thinktalkbuild.com  

It is an API so it returns JSON.   For humans there is a UI at https://contracthub.thinktalkbuild.com/ which consumes this API, it has a separate [repo for the U](https://github.com/KateSant/contract-reader-ui/tree/main) 

It works by looking for word patterns, configured in [this yaml](https://github.com/KateSant/contract-reader-engine/blob/main/src/main/resources/application.yml) 


# Tech Stack

* Java
* Springboot
* Junit
* Docker
* Terraform
* Google Cloud Platform, GCR
* Github Actions

# Useful links

* GCP console: https://console.cloud.google.com/run?project=contract-reader
* Github Action workflow: https://github.com/KateSant/contract-reader-engine/actions/workflows/CD-on-dispatch-deploy-to-prod.yml
* Terraform Cloud: https://app.terraform.io/app/kate-dev/workspaces/gcp-contractreader-engine

