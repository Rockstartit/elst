# ReadMe

Connect to instance:

    ssh -i <ssh-key>  <username>@<hostname>

    ssh -i elst-deployment ubuntu@330ce66f-b890-4be8-a01c-ed122261bf8c.ka.bw-cloud-instance.org

## Deployment

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