package com.github.biconou.couchbase.samples;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonParser;
import com.couchbase.client.deps.com.fasterxml.jackson.core.type.TypeReference;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectReader;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SimpleN1QLTest {


    private static Cluster cluster;
    private static Bucket bucket;

    @BeforeClass
    public static void setup() {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder().connectTimeout(10000).build();
        cluster = CouchbaseCluster.create(env,"localhost");
        bucket = cluster.openBucket("travel-sample");

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
    public void listDistinctTypes() {
        // Perform a N1QL Query
        N1qlQueryResult result = bucket.query(
                N1qlQuery.simple("SELECT distinct type FROM `travel-sample`"));

        // Print each found Row
       List<String> types = StreamSupport.stream(result.spliterator(),false)
                .map(p -> p.value().getString("type"))
                .collect(Collectors.toList());


         types.forEach(System.out::println);
    }

}
