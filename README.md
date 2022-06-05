# HER-LSF

## Requirements

- npm https://docs.npmjs.com/downloading-and-installing-node-js-and-npm
- Clojure cli https://clojure.org/guides/install_clojure
- babashka https://github.com/babashka/babashka (optional task runner)
- shadow-cljs https://github.com/thheller/shadow-cljs

## Installation

After cloning, the first thing to do is running
```
npm install
```

The rest of the README assumes, that you have shadow-cljs installed as a global tool. You can do this with

```
npm install -g shadow-cljs
```

For a way to get by without this, refer to the shadow-cljs documentation listed above.


## Script Running

All script tasks can be triggered using npm or babashka. This means that npm run commands can be replaced with bb. For example, the following commands are equivalent
```
npm run dev
bb run dev
```
For simplicity, the rest of the README will use npm run, because it does not require any further installation.

A list of available build scripts can be found in either bb.edn, or package.json.

## Running the App

To run the development web-server with hot-reload using shadow-cljs, use
```
npm run dev
```
To run the app via electron

## Release

To make an app ready for distribution, use
```
npm run build
npm run make
```

To test the app, run

```
npm run start
```

## Troubleshooting

If there are problems with the electron permissions, try giving it execute permissions with something like
```
chmod +x ./node_modules/.bin/electron
```
In case your shadow-cljs throws error message along the lines of "Cannot invoke \"Object.getClass()\" because \"target\" is null" but still compiles fine,
try downgrading your java version to something below version 15, to get rid of the errors.
