# OkhttpWrapper

This is a android Library which works as a wrapper on okhttp and used to call apis with the help of okhttp.

```java
CreateRequestApi createRequestApi = new CreateRequestApi.CreateBuilder(MainActivity.this)
                .addHeader("X-AUTHKEY","XXXXXXXX")
                .addRequestType(CreateRequestApi.REQUEST_TYPE.GET)
                .addParams("key","value")
                .build(response);
                createRequestApi.runApi("http://XXXXXXXXX");
```
In the build method you have call an interface to get the response 
```java
private ApiIterfaces.GetResponse response = new ApiIterfaces.GetResponse() {
        @Override
        public void getResponse(String response) {
           
        }

        @Override
        public void getErrorResponse(int code, String errorResponse) {
            
        }

        @Override
        public void noInternetConnection() {

        }
    };
```
###To send Json

Add the json object to this parameter.

```java
                .addJsonForPostApi(object)
                .build(response);
```

# Send Media Files

```java
                    createRequestApi = new CreateRequestApi.MediaMultipartBulider(MainActivity.this)
                    .addHeader("","")
                    .addMultipartImageFile("key", "fileName", "filePath")
                    .addMultipartVideoFile("key", "fileName", "filePath")
                    .build(response);
```
### Show Progress Dialog

This library support the progress dialoge so that you don't have to define it in your code. You can use any of the method as per your requirment. (This is initalized after calling the build method)

```java

    createRequestApi.showDialog("title", "message");
 
    createRequestApi.showDialog("message");
  
    createRequestApi.showDialog();
   
```

# Gradle

```java
dependencies {
    compile 'rishi.okhttp.wrapper:okhttp-wrapper:1.3'
}
```

# Maven

```java
<dependency>
  <groupId>rishi.okhttp.wrapper</groupId>
  <artifactId>okhttp-wrapper</artifactId>
  <version>1.3</version>
  <type>pom</type>
</dependency>
```
# Dependency

```java
compile 'com.squareup.okhttp3:okhttp:3.4.1'
```

# Permission Required

```java
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.INTERNET"/>
```

# License

```java

Copyright 2013 Square, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```
