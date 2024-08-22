#!/bin/bash
images=(
#  "discovery-server"
  "api-gateway"
#  "identity"
#  "users"
#  "games"
#  "sessions"
)

for img in "${images[@]}"; do
  docker rmi fbin243/${img}:0.0.1
  cd services/${img}/; mvn compile jib:dockerBuild; cd ..; cd ..
#  docker push fbin243/${img}
done

