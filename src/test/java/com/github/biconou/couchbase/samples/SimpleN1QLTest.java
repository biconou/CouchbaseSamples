package com.github.biconou.couchbase.samples;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleN1QLTest {


    private static Cluster cluster;
    private static Bucket bucket;

    @BeforeClass
    public static void setup() {
        cluster = CouchbaseCluster.create("localhost");
        bucket = cluster.openBucket("beer-sample");

        // Create a N1QL Primary Index (but ignore if it exists)
        bucket.bucketManager().createN1qlPrimaryIndex(true, false);

    }

    @Test
    public void listCountries() {
        // Perform a N1QL Query
        N1qlQueryResult result = bucket.query(
                N1qlQuery.parameterized("SELECT country FROM `beer-sample` where type = $1",
                        JsonArray.from("brewery"))
        );

        // Print each found Row
        for (N1qlQueryRow row : result) {
            System.out.println(row.value());
        }
        System.out.println(result.info().resultCount());
    }

    @Test
    public void listDistinctCountries() {
        // Perform a N1QL Query
        N1qlQueryResult result = bucket.query(
                N1qlQuery.parameterized("SELECT distinct country FROM `beer-sample` where type = $1",
                        JsonArray.from("brewery"))
        );

        // Print each found Row
        for (N1qlQueryRow row : result) {
            System.out.println(row.value());
        }
        System.out.println(result.info().resultCount());
    }

}
