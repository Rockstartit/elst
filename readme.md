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

Navigate to code folder

    cd danielaugustin/code

Build images:

    docker compose build

Run application stack:

    docker compose up
