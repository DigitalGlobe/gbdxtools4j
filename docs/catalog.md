gbdxtools4j: Java tools for using GBDX
======================================


## Simple Catalog Examples

### Simple Search:
```java
package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Record;
import com.digitalglobe.gbdx.tools.catalog.model.SearchRequest;
import com.digitalglobe.gbdx.tools.catalog.model.SearchResponse;


public class SimpleSearch {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        //
        // simple search
        //
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.withSearchAreaWkt( "POLYGON ((-122.41189956665039 37.59415685597818, -122.41189956665039 37.64460175855099, -122.34529495239259 37.64460175855099, -122.34529495239259 37.59415685597818, -122.41189956665039 37.59415685597818))")
                .withStartDate("2004-01-01T00:00:00.000Z")
                .withEndDate("2015-04-30T23:59:59.999Z")
                .withFilters(Arrays.asList("(sensorPlatformName = 'WORLDVIEW01' OR sensorPlatformName ='QUICKBIRD02')",
                        "cloudCover < 10",
                        "offNadirAngle < 10"))
                 .withTypes(Collections.singletonList("Acquisition"));

        SearchResponse response = catalogManager.search(searchRequest);

        System.out.println( "got a total of " + response.getStats().getRecordsReturned() + " records returned" );
        for( Record nextRecord: response.getResults() ) {
            System.out.println( "got record id of \"" + nextRecord.getIdentifier() + "\" of type \"" + nextRecord.getType() + "\"" );
        }

    }
}

```

### Spatial Search
```java
package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.Collections;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Record;
import com.digitalglobe.gbdx.tools.catalog.model.SearchRequest;
import com.digitalglobe.gbdx.tools.catalog.model.SearchResponse;


public class SpatialSearch {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();


        //
        // Spatial search
        //
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.withSearchAreaWkt( "POLYGON ((-122.41189956665039 37.59415685597818, -122.41189956665039 37.64460175855099, -122.34529495239259 37.64460175855099, -122.34529495239259 37.59415685597818, -122.41189956665039 37.59415685597818))")
                .withFilters(Collections.singletonList("offNadirAngle < 10"))
                .withTypes(Collections.singletonList("Acquisition"));

        SearchResponse response = catalogManager.search(searchRequest);

        System.out.println( "got a total of " + response.getStats().getRecordsReturned() + " records returned" );
        for( Record nextRecord: response.getResults() ) {
            System.out.println( "got record id of \"" + nextRecord.getIdentifier() + "\" of type \"" + nextRecord.getType() + "\"" );
        }
    }
}
```

### Date range search

```java
package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.Collections;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Record;
import com.digitalglobe.gbdx.tools.catalog.model.SearchRequest;
import com.digitalglobe.gbdx.tools.catalog.model.SearchResponse;


public class DateRangeSearch {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        //
        // date range search
        //
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.withStartDate("2015-04-30T00:00:00.000Z")
                .withEndDate("2015-04-30T23:59:59.999Z")
                .withFilters(Collections.singletonList("cloudCover < 10"))
                .withTypes(Collections.singletonList("Acquisition"));

        SearchResponse response = catalogManager.search(searchRequest);

        System.out.println( "got a total of " + response.getStats().getRecordsReturned() + " records returned" );
        for( Record nextRecord: response.getResults() ) {
            System.out.println( "got record id of \"" + nextRecord.getIdentifier() + "\" of type \"" + nextRecord.getType() + "\"" );
        }
    }
}

```

### Vendor Search
```java
package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.Collections;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Record;
import com.digitalglobe.gbdx.tools.catalog.model.SearchRequest;
import com.digitalglobe.gbdx.tools.catalog.model.SearchResponse;


public class VendorSearch {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        //
        // Search for Vendor by Name
        //
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.withFilters(Collections.singletonList("name like 'D%'"))
                .withTypes(Collections.singletonList("Vendor"));

        SearchResponse response = catalogManager.search(searchRequest);

        System.out.println("got a total of " + response.getStats().getRecordsReturned() + " records returned");
        for (Record nextRecord : response.getResults()) {
            System.out.println("got record id of \"" + nextRecord.getIdentifier() + "\" of type \"" + nextRecord.getType() + "\"");
        }
    }
}
```
### Get a single record
```java
package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Record;


public class GetRecord {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        Record record = catalogManager.getRecord("10100100057C1900");
        if( record != null ) {
            System.out.println( "got record of " + record.toString() );
        }
        else
            System.out.println("no records found");
    }
}
```

### Traverse a record

```java
package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.Collections;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.SearchResponse;
import com.digitalglobe.gbdx.tools.catalog.model.TraverseRequest;


public class TraverseRecord {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        TraverseRequest traverseRequest = new TraverseRequest();
        traverseRequest.withRootRecordId("10100100057C1900")
                        .withMaxdepth(2)
                        .withDirection("both")
                        .withLabels(Collections.singletonList("_imageFiles"));

        SearchResponse searchResponse = catalogManager.traverseFromRecord(traverseRequest);

        if( searchResponse != null ) {
            System.out.println( "got searchResponse of " + searchResponse.toString() );
        }
        else
            System.out.println("no searchResponse found");
    }
}
```