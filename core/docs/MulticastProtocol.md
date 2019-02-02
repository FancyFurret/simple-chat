# Multicast Protocol
Multicast is used to find other Simple Chat clients on the network. It's only use is to get the IP address of these 
other users. No other information is sent over this layer. `simplechat_send` and `simplechat_recv` are used as a prefix 
to make sure we aren't accidentally communicating with the devil.

## Actions

### Get IP Address
Gets the IP address of everyone in the multicast group.

#### Send:
    {
        "mode": "simplechat.send",
        "action": "get_ip" 
    }

#### Receive:
    {
        "mode": "simplechat.recv",
        "action": "get_ip",
        "args": {
            "ip": "xxx.xxx.xxx.xxx"
        }
    }
