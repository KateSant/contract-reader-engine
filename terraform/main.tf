provider "google" {
  credentials = file("/Users/kate/gcp-creds.json")
  project     = "contract-reader"
  region      = "europe-west4"
}


resource "google_cloud_run_service" "default" {
  name     = "cloudrun-service-terraformed"
  location = "europe-west4"

  template {
    spec {
      containers {
        image = "us-docker.pkg.dev/cloudrun/container/hello"
      }
    }
  }

  traffic {
    percent         = 100
    latest_revision = true
  }
}