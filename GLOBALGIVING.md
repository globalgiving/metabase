# Building, Running, and Deploy GlobalGiving's Forked Metabase


## Building Locally

See also https://www.metabase.com/docs/latest/developers-guide.html

    ./bin/build

#$ Create, tag, and push Docker image

    docker build -t globalgiving/metabase:v0.36.2.1 .
    docker push globalgiving/metabase:v0.36.2.1

## Deploy Locally

    docker run -d -p 3000:3000 --name metabase globalgiving/metabase:v0.36.2.1

## Deploy to Production

### Update CDK to use new Docker Image

Edit `/cdk-infrastructure/globalgiving/user-data-scripts/metabase-server.sh.j2`

Specifically, update lines 33 & 34 to update the version tag to whatever the most recent is. For example (truncated):

    docker pull globalgiving/metabase:v0.36.2.1
    docker run -d -p 50000:3000 -e ..... globalgiving/metabase:v0.36.2.1


### Deploy CDK Changes

Then, per instructions in `cdk-infrastructure`:

    cd ~/git/cdk-infrastructure
    pipenv shell
    pipenv sync -d
    cdk ls
    cdk diff us-east-1-backend
    cdk deploy us-east-1-backend
