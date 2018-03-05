bind_addr = "0.0.0.0" # the default

data_dir  = "/nomad/data"

advertise {
  # Defaults to the node's hostname. If the hostname resolves to a loopback
  # address you must manually configure advertise addresses.
  http = "192.168.143.138:4646"
  # rpc  = "1.2.3.4"
  serf = "192.168.143.138:4648" # non-default ports may be specified
}

server {
  enabled          = true
  bootstrap_expect = 1
}

# client {
#   enabled       = true
#   network_speed = 10
#   options {
#     "driver.raw_exec.enable" = "1"
#    }
# }

# consul {
#   address = "1.2.3.4:8500"
# }
