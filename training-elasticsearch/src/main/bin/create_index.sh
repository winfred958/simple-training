#!/bin/bash

HOME_DIR_PATH=$(cd "$(dirname "$0")"; cd ..;  pwd)

source "${HOME_DIR_PATH}/bin/elasticsearch-env.sh"

INDEX_NAME=$1
MAPPING_DATA_PATH=$2

CMD=$(cat << EOF
curl --request PUT \
     --url '${ELASTICSEARCH_HTTP_URL}/${INDEX_NAME}' \
     --data '${MAPPING_DATA_PATH}'
EOF
)

response=${CMD}
echo -e "/n${response}\n"

