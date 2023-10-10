# ETA
> CS5132 PA2: Priority Queue Pass It On Delivery System 
> 
> By Hugo, Kabir, Mahir 

ETA is a JavaFX application we created to help charities deliver goods and groceries to vulnerable people by using the everyday route of drivers. 

Users have to first input their daily route using our simple map ui. This route will be persisted for subsequent usage. They then are able to see all the paths given by charities, and are able to reserve the path with the lowest estimated time of arrival when taking that detour along with their daily route. Clicking on the RSVP button will give them a link for the route in Google Maps. To ensure that a driver will not misuse the system, they can only reserve one route and will have to finish that route to reserve another route.

There is also an interface for charities to put the routes using the interactive map. After inputting the route, it will be added to a priority queue, where the priority is the negative of the ETA of the total route.
