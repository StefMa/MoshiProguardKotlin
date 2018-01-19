# MoshiProguardKotlin

Just a simple example that obfuscation will break the `@Json` annotation in Moshi.

## How to
Just clone this issue and run the `debug` and notice that it works.
The TextView should display the correct values which can be found here: https://jsonplaceholder.typicode.com/posts

Now run the `release` variant. Notice that only the property `notId` is `null`!
Even if we keep the whole class with proguard...
