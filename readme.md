# ReadMe

Connect to instance:

    ssh -i <ssh-key>  <username>@<hostname>

    ssh -i elst-deployment ubuntu@ee60dea8-624b-4422-b153-d96c6a404142.ka.bw-cloud-instance.org

## Deployment

Docker Engine installieren

    https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository

Clone/Pull Repository:

    git clone <repository-url>

    git clone https://gitlab.kit.edu/kit/kastel/sdq/stud/abschlussarbeiten/masterarbeiten/danielaugustin.git

    git pull

Configure frontend environment variables (.env.local):

    BASE_PATH=<base-path>
    AUTH0_DOMAIN=<auth0-domain>
    AUTH0_CLIENT_ID=<auth0-client-id>
    AUTH0_AUDIENCE=<auth0-api-audience>

    BASE_PATH=/
    AUTH0_DOMAIN=elst.eu.auth0.com
    AUTH0_CLIENT_ID=JQ3G7d5UXPeDl3o4zkQcg0DHvCoGJjr8
    AUTH0_AUDIENCE=https://elst.de/api/

Navigate to code folder

    cd danielaugustin/code

Build images:

    docker compose build

Run application stack:

    docker compose up

If memory is a problem:

    docker system prune -a
