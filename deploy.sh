#!/bin/bash
images=(
  "discovery-server"
  "api-gateway"
  "identity"
  "users"
  "games"
  "events"
  "sessions"
  "notifications"
) # Adjust this list base on purpose

# Stop/Remove all containers
cd docker-compose; docker compose down; cd ..

mvn clean

for img in "${images[@]}"; do
  # Remove the old images
  docker rmi fbin243/${img}

  # Build new image with latest tag
  cd services/${img}/; mvn compile jib:dockerBuild; cd ..; cd ..

  # Push image to docker hub (just use for update)
  docker push fbin243/${img}
done

# Run all of containers
cd docker-compose; docker compose up -d; cd ..
