# Engage JAVA SDK

The Engage java SDK lets you capture user attributes and events on your site. You can later use this on Engage to create user segments for analytics, broadcast messages and automation messages.

## Getting Started

[Create an Engage account](https://engage.so/) to get your API key


## Installation

```ssh
 
```

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
## Set New Client
Change the `userResource` client

```java
userResource.setClient(new EngageClient("key", "secret"));
```
## License

MIT