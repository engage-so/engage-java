# Engage JAVA SDK

The Engage java SDK lets you capture user attributes and events on your site. You can later use this on Engage to create user segments for analytics, broadcast messages and automation messages.

## Getting Started

[Create an Engage account](https://engage.so/) to get your API key


## Installation

Download `engage-java.jar` and add jar file as a Module to your Java project:

Download from https://github.com/engage-so/engage-java/releases/tag/v1.0.0

On Intellij IDEA: File -> Project Structure -> Modules -> Dependencies Tab -> Add -> JARs or Directories -> Attach jar

On Netbeans: Project properties -> Libraries -> Compile -> ADD JAR/folder -> Add Jar

## Configuration

Initialise the SDK with your public and private key. Your keys are available in the settings page of your Engage dashboard.

```java
EngageClient client = new EngageClient("key", "secret");
```

## Identifying users

You only need a unique identifier that represents the user on your platform to track their events and attributes on Engage.

Create a user resource object and pass the above client to it.

```java
UserResource userResource = new UserResource(client);

//create the data to identify the user
HashMap<String, Object> data = new HashMap<>(){{
    put("id","U424");
    put("email","newuser@gmail.com");
}};

JSONObject response = userResource.identify(data);
```

`id` represents the unique identifier for the user on your platform. It is the only required parameter. You can send any other attribute you want e.g. `age`, `plan`. Here are the standard ones we use internally on the user profile:
- `first_name`
- `last_name`
- `email`
- `number` (with international dialing code without the +)
- `created_at` (represents when the user registered on your platform. If not added, Engage sets it to the current timestamp.)


## Update/add user attributes

If you need to add new attributes or update an existing attribute, you can use the `addAttribute` method.

```java
long l = Long.parseLong("608514751443");

HashMap<String, Object> data = new HashMap<>(){{
    put("first_name","testing");
    put("number", l);
}};

JSONObject response = userResource.addAttribute("U424", data);
```

(You can also use `identify` to update or add new attributes.)

## Tracking user events and actions

You can track user events and actions in a couple of ways.

Tracking an event with no value:

```java
JSONObject response = userResource.track("U424", "purchase");
```

Tracking an event with a value:

```java
HashMap<String, Object> data = new HashMap<>(){{
    put("event","new sign");
    put("value", true);
}};

JSONObject response = userResource.track("U424", data);
```

`event` is the event you want to track. `value` is the value of the event. This can be a string, number or boolean. There is an optional `timestamp` parameter. If not included, Engage uses the current timestamp. The timestamp value must be a valid datetime string.

If you need to track more properties with the event, you can track it this way:

```java
HashMap<String, Object> data = new HashMap<>(){{
    put("event","new sign");
    put("properties", 
    HashMap<String, Object>(){{
            put("product","T123");
            put("currency", "USD");
            put("amount", 12.99);
        }};
    );
}};
```
## Convert user to account

Converts a customer/user to account

```java
JSONObject response = userResource.convertToAccount("uid");
```

## Convert account to customer/user

Converts a account to customer

```java
JSONObject response = userResource.convertToCustomer("aid");
```

## Add user to an account

This adds a user to an account if it exists

```java
JSONObject response = userResource.addToAccount(uid, aid, role)
```
Where
- uid  is the user id 
- aid is the account id
- role is the role of the user for the account; This can be null


## Change a user's role in an account

This changes a user's role in an account

```java
JSONObject response = userResource.changeUserRole(uid, aid, role)
```

## Remove a user from an account

This removes a user from an account

```java
JSONObject response = userResource.removeFromAccount(uid, aid);
```


## Set New Client
Change the `userResource` client

```java
userResource.setClient(new EngageClient("key", "secret"));
```
## License

MIT