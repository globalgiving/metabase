# Build, Run, and Deploy GlobalGiving's Forked Metabase


## Building Locally

See also https://www.metabase.com/docs/latest/developers-guide.html

    ./bin/build

## Create, tag, and push Docker image

    docker build -t globalgiving/metabase:v0.36.2.1 .
    docker push globalgiving/metabase:v0.36.2.1

## Run Locally

    docker run -d -p 3000:3000 --name metabase globalgiving/metabase:v0.36.2.1
    
For more on running a local copy of our fork for ETL/data stack testing purposes, [see the ETL docs](https://github.com/globalgiving/etl#exploring-etled-data-locally-via-metabase)

## Deploy to Production

### Update Docker Image

You must update the Docker image via command line to deploy.

    ssh-add ~/.ssh/Metabase.pem
    ssh bastion.cl.globalgiving.org
    ssh metabase
    docker pull globalgiving/metabase:v0.36.2.1
    docker stop metabase
    docker rm metabase
    ## Note - full command is available on `/cdk-infrastructure/globalgiving/user-data-scripts/metabase-server.sh.j2`
    docker run -d -p 50000:3000 -e ..... globalgiving/metabase:v0.36.2.1

### Update CDK to use new Docker Image

Although you've deployed manually, you should also update CDK so that if the EC2 instance dies or is recreated, it will properly reload metabase. 

Edit `/cdk-infrastructure/globalgiving/user-data-scripts/metabase-server.sh.j2`. Specifically, update lines 33 & 34 to update the version tag to whatever the most recent is. For example (truncated):

    docker pull globalgiving/metabase:v0.36.2.1
    docker run -d -p 50000:3000 -e ..... globalgiving/metabase:v0.36.2.1

Then, per instructions in `cdk-infrastructure`:

    cd ~/git/cdk-infrastructure
    pipenv shell
    pipenv sync -d
    cdk ls
    cdk diff us-east-1-backend
    cdk deploy us-east-1-backend
