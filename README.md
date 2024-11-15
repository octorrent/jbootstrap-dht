jbootstrap-dht
=============

The DHT bootstrap server can be used as an introducer to the sectorrent
DHT network. Like the ones running at `router.sectorrent.com`.

SecTorrent clients can use this server to join the DHT, assuming some number
of clients are agreeing on using the same server.

If you want to use the [Rust](https://github.com/sectorrent/rbittorrent-dht) version.

The command line options are:

	usage: jbootstrap-dht <external-IP> [options]

	OPTIONS:
	--help                prints this message.
	--nodes <n>           sets the number of nodes to keep in
	                      the node buffer. Once full, the oldest
	                      nodes are replaced as new nodes come in.
	--ping-queue <n>      sets the max number of nodes to keep in
	                      the ping queue. Nodes are held in the queue
	                      for 15 minutes.
	--no-verify-id        disable filtering nodes based on their node ID
	                      and external IP (allow any node in on the
	                      node list to hand out).
	--ipv6 <ip>           listen for IPv6 packets on the given address
	                      can be specified more than once
	--version <version>   The client version to insert into all outgoing
	                      packets. The version format must be 2 characters
	                      followed by a dash and an integer.
	--dir <path>          specify the directory where the node buckets are
	                      stored on disk. Defaults to ".".
	--port <listen-port>  Sets the port to listen on (for all interfaces)
	                      defaults to 6881
	--x-pollinate <ip> <port>
	                      if the ping queue becomes too small, request more
	                      nodes from this DHT node.

The first argument when launching the server is its own IP. This is not
only relevant for binding the socket to this interface but is also used when
generating its own DHT node ID.

The number of DHT nodes to keep in the cache of nodes to hand out defaults
to 10000000 and can be overridden by `--nodes`. It may not be lower than
1000.

The ping queue is the max number of nodes allowed in the queue waiting to
be pinged. Nodes are (ideally) pinged 15 minutes after seen. If too many
nodes are pinging the dht-bootstrapper to hold them all for 15 minutes
in the ping buffer, the rate of pinging is throttled to match.

For example, the default of 5000000 nodes in the ping buffer restricts the
ping rate to (5000000 / 900 = ) 5555 pings per second. Experience from
`router.sectorrent.com` suggests that about 45% of pings succeeds and end
up being added to the node buffer. At the default rate, that means the
entire node buffer is replaced every 66 minutes. i.e. once you're in the
node buffer, you'll be handed out to nodes for abut 66 minutes before you
are replaced by someone else.

The current rate at `router.sectorrent.com` is about 20000 requests per second.
Every request returns 16 nodes. That means every node in the node buffer is
handed out once every (10000000 / 16 / 20000 ~=) 31 seconds.

Only nodes whose node ID match their external IP address (according to this_)
are pinged.

`jbootstrap-dht` listens on port 6881 by default. You may specify a different
port using the `--port` command line option.

The source for the bootstrap server is released under the MIT license.
Please contribute back fixes and improvements!
