package org.sectorrent.jbootstrapdht;

import org.sectorrent.jlibdht.Kademlia;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args)throws SocketException, UnknownHostException {
        Kademlia dht = new Kademlia();

        String externalIP = null, ipv6 = null, version = null, directory = ".", pollinateIp = null;
        int nodes = 0, pingQueue = 0, port = 6881, pollinatePort = -1;

        for(int i = 0; i < args.length; i++){
            switch(args[i]){
                case "--help":
                    printHelp();
                    return;

                case "--nodes":
                    if(i + 1 < args.length){
                        nodes = Integer.parseInt(args[++i]);

                    }else{
                        System.err.println("Missing value for --nodes");
                    }
                    break;

                case "--ping-queue":
                    if(i + 1 < args.length){
                        pingQueue = Integer.parseInt(args[++i]);

                    }else{
                        System.err.println("Missing value for --ping-queue");
                    }
                    break;

                case "--no-verify-id":
                    dht.getRoutingTable().setSecureOnly(false);
                    break;

                case "--ipv6":
                    if(i + 1 < args.length){
                        ipv6 = args[++i];

                    }else{
                        System.err.println("Missing value for --ipv6");
                    }
                    break;

                case "--version":
                    if(i + 1 < args.length){
                        version = args[++i];

                    }else{
                        System.err.println("Missing value for --version");
                    }
                    break;

                case "--dir":
                    if(i + 1 < args.length){
                        directory = args[++i];

                    }else{
                        System.err.println("Missing value for --dir");
                    }
                    break;

                case "--port":
                    if(i + 1 < args.length){
                        port = Integer.parseInt(args[++i]);

                    }else{
                        System.err.println("Missing value for --port");
                    }
                    break;

                case "--x-pollinate":
                    if(i + 2 < args.length){
                        pollinateIp = args[++i];
                        pollinatePort = Integer.parseInt(args[++i]);

                    }else{
                        System.err.println("Missing value for --x-pollinate");
                    }
                    break;

                default:
                    if(externalIP == null){
                        externalIP = args[i];
                        dht.getRoutingTable().setExternalAddress(InetAddress.getByName(args[i]));

                    }else{
                        System.err.println("Unknown option: " + args[i]);
                    }
                    break;
            }
        }

        dht.bind(port);
    }

    private static void printHelp(){
        System.out.println("Usage: jbootstrap-dht <external-IP> [options]");
        System.out.println("\nOPTIONS:");
        System.out.println("--help                prints this message.");
        System.out.println("--nodes <n>           sets the number of nodes to keep in the node buffer.");
        System.out.println("--ping-queue <n>      sets the max number of nodes in the ping queue.");
        System.out.println("--no-verify-id        disable filtering nodes based on their node ID and IP.");
        System.out.println("--ipv6 <ip>           listen for IPv6 packets on the given address.");
        System.out.println("--version <version>   specify the client version for outgoing packets.");
        System.out.println("--dir <path>          specify the directory for storing node buckets.");
        System.out.println("--port <listen-port>  set the port to listen on (defaults to 6881).");
        System.out.println("--x-pollinate <ip> <port>  request more nodes from the given DHT node.");
    }
}
