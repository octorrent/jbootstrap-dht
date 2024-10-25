package org.octorrent.jbootstrapdht;

import org.octorrent.jlibdht.Kademlia;

import java.net.SocketException;

public class Main {

    public static void main(String[] args)throws SocketException {
        Kademlia dht = new Kademlia();
        dht.getRoutingTable().setSecureOnly(false);
        dht.bind(6881);
    }
}
