PATH_OF_ENVOY_DEMO=envoy-demo.yaml
PATH_OF_ENVOY_OVERRIDE=envoy-override.yaml
envoy -c $PATH_OF_ENVOY_DEMO --config-yaml "$(cat $PATH_OF_ENVOY_OVERRIDE)"