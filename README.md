# CouchbaseSamples

Installation rapide avec Docker
https://developer.couchbase.com/documentation/server/4.6/getting-started/do-a-quick-install.html
Documentation sur les images docker : https://github.com/couchbase-guides/couchbase-docker


On démarre un premier noeud qui contient des données

docker run -t --name couchbase-sandbox -p 8091-8094:8091-8094 -p 11210:11210 -d couchbase/sandbox:5.0.0-beta
accéder à l'interface d'administration http://localhost:8091/ : Administrator/password
La version est : Version: 4.5.0-2601 Enterprise Edition (build-2601)

Onglet "Server Nodes"
![](./screen_1.png)

Sur cet écran on note qu'il y a un seul noeud dans notre cluster (on note au passage son adresse IP docker 172.17.0.2)
Noter l'alerte "Fail Over Warning ..."

Onglet "Data Buckets"
On note la présence d'un bucket nommé travel-sample contenant 31 591 documents
Si on édite la conf du bucket on remarque qu'il est paramétré avec 1 replicat




Création d'un second noeud
docker run -t --name couchbase-node-2 -p 8095-8098:8091-8094  -d couchbase/server:community-5.0.0

docker network inspect bridge

-> nous donne l'adresse du second noeud : 172.17.0.3

Depuis l'interface d'admin du premier noeud, on fait un ADD SERVER (en haut à droite).

On constate que le nouveau noeud n'est pas actif si on ne fait pas un rebalance.
Si on va voir le bucket travel-sample, on remarque qu'il n'y a qu'un seul noeud.
dans vbucket resources, on a 31.5 active par serveur et 0 replicas

On fait un rebalance.

On va revoir le bucket on a maintenant 2 noeuds et 1 replica









Start using the SDK
https://developer.couchbase.com/documentation/server/5.0/sdk/java/start-using-sdk.html
