package com.example.demo.util;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName: IPFSUtil
 * @Description: IPFS工具类
 * @Author: 刘敬
 * @Date: 2019/6/13 16:05
 **/
public class IPFSUtil {
    // private static IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
    private static IPFS ipfs = new IPFS("/ip4/39.105.13.150/tcp/5001");// 放在了阿里云上

    public static String add(String path) throws IOException {
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(path));
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toString();
    }

    public static byte[] cat(String hash) throws IOException {
        return ipfs.cat(Multihash.fromBase58(hash));
    }

}
