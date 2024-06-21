package com.controller;

import org.pcap4j.core.*;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NetworkController {

  private volatile List<Map<String, String>> packetList = new ArrayList<>();
  private String localIp;

  @GetMapping("/network")
  public String showNetworkConnections() {
    return "network/index";
  }

  @GetMapping("/interfaces")
  @ResponseBody
  public List<Map<String, String>> getNetworkInterfaces() {
    List<Map<String, String>> interfaces = new ArrayList<>();
    try {
      List<PcapNetworkInterface> allDevs = Pcaps.findAllDevs();
      for (PcapNetworkInterface nif : allDevs) {
        for (PcapAddress addr : nif.getAddresses()) {
          if (addr.getAddress() instanceof Inet4Address) {
            Map<String, String> iface = new HashMap<>();
            iface.put("name", nif.getName());
            iface.put("description", nif.getDescription());
            iface.put("address", addr.getAddress().getHostAddress());
            interfaces.add(iface);
            break;
          }
        }
      }
    } catch (PcapNativeException e) {
      System.out.println("Error: Native library not found. Please install the required native library:");
      System.out.println("For Windows: Download WinPcap from https://www.winpcap.org/install/default.htm");
      System.out
          .println("For Linux: Install libpcap using your package manager, e.g., sudo apt-get install libpcap-dev");
      System.out.println("For MacOS: Install libpcap using Homebrew, e.g., brew install libpcap");
    }
    return interfaces;
  }

  @GetMapping("/sniffer")
  @ResponseBody
  public Map<String, Object> startPacketSniffer(@RequestParam("interfaceName") String interfaceName,
      @RequestParam(value = "showLocalOnly", defaultValue = "false") boolean showLocalOnly) {
    Map<String, Object> response = new HashMap<>();
    packetList.clear();
  
    try {
      List<PcapNetworkInterface> allDevs = Pcaps.findAllDevs();
      System.out.println("Available Interfaces: ");
      allDevs.forEach(dev -> System.out.println(" - " + dev.getName()));
  
      PcapNetworkInterface nif = allDevs.stream()
          .filter(dev -> dev.getName().equals(interfaceName))
          .findFirst()
          .orElse(null);
  
      if (nif == null) {
        response.put("error", "No suitable network interface found.");
        return response;
      }
  
      localIp = nif.getAddresses().stream()
          .filter(addr -> addr.getAddress() instanceof Inet4Address)
          .map(addr -> addr.getAddress().getHostAddress())
          .findFirst()
          .orElse("Unknown");
  
      PcapHandle handle = nif.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 50);
  
      Thread packetCaptureThread = new Thread(() -> {
        try {
          handle.loop(-1, (Packet packet) -> {
            if (packet.contains(IpV4Packet.class)) {
              IpV4Packet ipPacket = packet.get(IpV4Packet.class);
              String srcAddr = ipPacket.getHeader().getSrcAddr().getHostAddress();
              String dstAddr = ipPacket.getHeader().getDstAddr().getHostAddress();
  
              if (showLocalOnly && !isLocal(srcAddr) && !isLocal(dstAddr)) return;
  
              Map<String, String> packetData = new HashMap<>();
              packetData.put("src", srcAddr);
              packetData.put("dst", dstAddr);
  
              synchronized (packetList) {
                packetList.add(packetData);
              }
            }
          });
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          handle.close();
        }
      });
  
      packetCaptureThread.setDaemon(true);
      packetCaptureThread.start();
  
      response.put("localIp", localIp);
      response.put("packets", packetList);
      return response;
    } catch (NoClassDefFoundError e) {
      response.put("error", "Native library not found. Please install the required native library: " +
          "<ul>" +
          "<li>Windows: <a href=\"https://www.winpcap.org/install/default.htm\">Download WinPcap</a></li>" +
          "<li>Linux: Install libpcap using your package manager (e.g., <code>sudo apt-get install libpcap-dev</code>)</li>" +
          "<li>MacOS: Install libpcap using Homebrew (e.g., <code>brew install libpcap</code>)</li>" +
          "</ul>");
      return response;
    } catch (Exception e) {
      response.put("error", "An error occurred: " + e.getMessage());
      return response;
    }
  }
  
  private boolean isLocal(String ip) {
    return ip.startsWith("192.168.") || ip.startsWith("10.") || ip.startsWith("172.16.") || ip.startsWith("172.31.");
  }
}
