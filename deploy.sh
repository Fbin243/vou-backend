#!/bin/bash
images=(
#  "discovery-server:0.0.1"
  "api-gateway:0.0.1"
#  "identity:0.0.1"
#  "users:0.0.1"
#  "games:0.0.1"
  "sessions:0.0.1"
)

cd services/discovery-server/; mvn compile jib:dockerBuild; cd ..; cd ..
cd services/api-gateway/; mvn compile jib:dockerBuild; cd ..; cd ..
cd services/identity/; mvn compile jib:dockerBuild; cd ..; cd ..
cd services/users/; mvn compile jib:dockerBuild; cd ..; cd ..
cd services/games/; mvn compile jib:dockerBuild; cd ..; cd ..
cd services/sessions/; mvn compile jib:dockerBuild; cd ..; cd ..

#for img in "${images[@]}"; do
#  docker push fbin243/${img}
#done

