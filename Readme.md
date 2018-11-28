*Rest API financial contract managment*

This API allow you to manipulate financial contracts

---

**Retreive all supports**

Return all the supports

* **URL**

    `/support`

* **Method**

    `GET`

* **Success Response:**

  * **Code:** 200
    **Content:**
    ```json
    [
         {
             "name": "support 0",
             "isin": "1234",
             "establishment": "establishment 0",
             "value": 10000,
             "creationDate": "2018-11-27T18:56:15.526+0000"
         },
         {
             "name": "support 1",
             "isin": "abcd",
             "establishment": "establishment 1",
             "value": 20000,
             "creationDate": "2018-11-27T18:56:15.526+0000"
         }
     ]
     ```

* **No result Response:**

  * **Code:** 200
    **Content:**
    ```json
    []
     ```

---

**Search Support by ISIN**

Return the unique support with the specified ISIN

* **URL**

    `/support/{isin}`

* **Method**

    `GET`

* **Success Response:**

  * **Code:** 200
    **Content:**
    ```json
    {
        "name": "support 1",
        "isin": "abcd",
        "establishment": "establishment 1",
        "value": 20000,
        "creationDate": "2018-11-27T19:40:55.064+0000"
    }
     ```
* **No result Response:**

  * **Code:** 204

---


**Search Supports by name**

Return a list with all support matching this name

* **URL**

    `/support/name/{name}`

* **Method**

    `GET`

* **Success Response:**

  * **Code:** 200
    **Content:**
    ```json
    [
        {
            "name": "support 0",
            "isin": "12345",
            "establishment": "establishment 0",
            "value": 10000,
            "creationDate": "2018-11-27T19:40:55.064+0000"
        },
        {
            "name": "support 0",
            "isin": "1234",
            "establishment": "establishment 0",
            "value": 10000,
            "creationDate": "2018-11-27T19:40:55.064+0000"
        }
    ]
     ```
* **No result Response:**

  * **Code:** 200
    **Content:**
    ```json
    []
     ```

---

**Add a support**

Allow you to add a new support or edit a support with the same ISIN

* **URL**

    `/support`

* **Method**

    `POST`

* **Data param**

    * **Content:**
    ```json
    {
        "name": "support 0",
        "isin": "1234",
        "establishment": "establishment 0",
        "value": 10000,
        "creationDate": "2018-11-27T19:40:55.064+0000"
    }
     ```

    * **Required:**

       * `name=[String]`
       * `isin=[String]`
       * `establishment=[String]`
       * `value=[Float]`
       * `creationDate=[Date]`

* **Success Response:**

  * **Code:** 200

* **Error Response:**

  * **Code:** 4**/5**


---

**Remove a support**

Allow you to remove a support identified by this ISIN code

* **URL**

    `/support/{isin}`

* **Method**

    `DELETE`

* **Success Response:**

  * **Code:** 200

---

**Retreive all contracts**

Return all the contracts

* **URL**

    `/contract`

* **Method**

    `GET`

* **Success Response:**

  * **Code:** 200
    **Content:**
    ```json
    [
        {
            "name": "contract 1",
            "establishment": "establishment 1",
            "currency": "DOLLAR",
            "amount": 2000,
            "openingDate": "2018-11-27T21:01:41.416+0000",
            "clientMail": "client 1",
            "supportParts": {}
        },
        {
            "name": "contract 0",
            "establishment": "establishment 0",
            "currency": "EURO",
            "amount": 1000,
            "openingDate": "2018-11-27T21:01:41.416+0000",
            "clientMail": "client 0",
            "supportParts": {
                "1234": 0.9
            }
        }
    ]
     ```

* **No result Response:**

  * **Code:** 200
    **Content:**
    ```json
    []
     ```

---

**Search contract by name**

Return the unique contract with the specified name

* **URL**

    `/contract/{name}`

* **Method**

    `GET`

* **Success Response:**

  * **Code:** 200
    **Content:**
    ```json
    {
        "name": "contract 0",
        "establishment": "establishment 0",
        "currency": "EURO",
        "amount": 1000,
        "openingDate": "2018-11-27T21:01:41.416+0000",
        "clientMail": "client 0",
        "supportParts": {
            "1234": 0.9
        }
    }
     ```
* **No result Response:**

  * **Code:** 204

---

**Search contracts by Support ISIN**

Return a list with all contracts that have parts of supports with this ISIN

* **URL**

    `/contract/isin/{isin}`

* **Method**

    `GET`

* **Success Response:**

  * **Code:** 200
    **Content:**
    ```json
    [
        {
            "name": "contract 0",
            "establishment": "establishment 0",
            "currency": "EURO",
            "amount": 1000,
            "openingDate": "2018-11-27T21:01:41.416+0000",
            "clientMail": "client 0",
            "supportParts": {
                "1234": 0.9
            }
        }
    ]
     ```
* **No result Response:**

  * **Code:** 200
    **Content:**
    ```json
    []
     ```

---

**Add a Contract**

Allow you to add a new contract or edit a contract with the same name

* **URL**

    `/contract`

* **Method**

    `POST`

* **Data param**

    * **Content:**
    ```json
    {
        "name": "contract 0",
        "establishment": "establishment 0",
        "currency": "EURO",
        "amount": 1000,
        "openingDate": "2018-11-27T21:01:41.416+0000",
        "clientMail": "client 0",
        "supportParts": {
            "1234": 0.9,
            "test":1.0
        }
    }
     ```
     or
    ```json
    {
        "name": "contract 0",
        "establishment": "establishment 0",
        "currency": "EURO",
        "amount": 1000,
        "openingDate": "2018-11-27T21:01:41.416+0000",
        "clientMail": "client 0"
    }
     ```

    * **Required:**

       * `name=[String]`
       * `establishment=[String]`
       * `currency={EURO;DOLLAR}`
       * `value=[Float]`
       * `creationDate=[Date]`

    * **Optionnal:**

       * `supportParts=[Map<isin, part>]`
            * `isin=[String]` (must be a known ISIN)
            * `part=[float]` (must be comprised between 0 and 1)



* **Success Response:**

  * **Code:** 200

* **Error Response:**

  * **Code:** 4**/5**

---

**Remove a contract**

Allow you to remove a contract identified by this name

* **URL**

    `/contract/{name}`

* **Method**

    `DELETE`

* **Success Response:**

  * **Code:** 200

---

**create an association between a contract and a support**

Allow you to add a new association or to edit an existing one

* **URL**

    `/association/{contractName}/{isin}/{part}`

Note : contractName and isin must correspond to existing contract and support

* **Method**

    `POST`

* **Success Response:**

  * **Code:** 200


---

**Remove an association**

Allow you to remove an association between a contract and a support

* **URL**

    `/association/{contractName}/{isin}"`

* **Method**

    `DELETE`

* **Success Response:**

  * **Code:** 200
