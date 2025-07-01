#!/bin/bash

# Create the database user
sudo -u postgres psql -c "CREATE USER oauth2user WITH PASSWORD 'oauth2password';"

# Create the database
sudo -u postgres psql -c "CREATE DATABASE oauth2db;"

# Grant privileges
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE oauth2db TO oauth2user;"
