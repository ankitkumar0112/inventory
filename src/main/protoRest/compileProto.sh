PROTO_PARENT_PATH="../proto/"

DEST_PATH_OF_SERVICE_DESCRIPTOR="./protos.pb"

PROTO_FILE_PATH="../proto/*.proto"

protoc -I /proto-google-common-protos -I $PROTO_PARENT_PATH --include_imports --include_source_info\
  --descriptor_set_out=$DEST_PATH_OF_SERVICE_DESCRIPTOR\
  $PROTO_FILE_PATH\



# PROTO_PARENT_PATH = parent folder of proto files (i.e. ./src/main/proto)
# DEST_PATH_OF_SERVICE_DESCRIPTOR = file path where you want to keep your service descriptor file (i.e. ./src/main/envoy/order-proto.pb)
    # you'll need to create this file beforehand
# PROTO_FILE_PATH = full file path of each proto file (i.e ./src/main/proto/com/brightly/microservice/orders/order.proto)
    # in the case your team has more than one proto file, include each path as a separate argument (i.e. ./src/main/proto/com/brightly/microservice/orders/order.proto ./src/main/proto/com/brightly/microservice/orders/order2.proto)