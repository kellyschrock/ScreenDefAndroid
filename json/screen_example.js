// FlyFragment opens, and calls /ui/flight. This comes back.
{
	controls: [
		{
			type: "image_button",
			id: "smart_shots",
			worker_id: "shot_manager",
			icon: "img/smart_shots.png",
			location: "bottom_left_panel"
		}
		// This is an ImageButton with an icon served by shot_manager.
		// It's located in a panel at the bottom left of the flight screen.
		// The id is "smart_shots_screen".
		// When clicked, it calls the shot_manager worker with an id of "smart_shots".
		// In response, ShotManager fires a WS message to display a screen, as shown below.
	]
}

// This is sent when the user clicks "smart_shots" above.

{
	id: "display_screen", // Tell the app to display a screen
	screen: {
		id: "smart_shots",
		location: "flight",
		type: "fullscreen",
		title: "Smart Shots",
		summary: "These are the smart shots installed on your vehicle. Click one to use it.",
		list_items: [
			{id: "start_shot", worker_id: "shot_orbit", name: "Orbit", description: "Orbits around a point"},
			{id: "start_shot", worker_id: "shot_selfie", name: "Selfie", description: "Takes a selfie"},
			{id: "start_shot", worker_id: "shot_mpcc", name: "Cable Cam", description: "Does some wild stuff"}
		],
		buttons: [
			{ id: "close", name: "Close" } // "close" is a known ID that just closes the screen
		]
	}
}

// TODO: How does the screen know when to close?

// When "Selfie" is clicked, the app calls "shot_selfie" with "start_shot". In response, shot_orbit fires these messages back:

{ // Close the current screen if it's open.
	id: "close_screen", 
	id: "smart_shots"
}

{
	id: "display_screen",
	screen: {
		id: "orbit_shot",
		location: "flight",
		type: "shot_screen",
		title: "Orbit",
		summary: "Point the target at the object you want to orbit, and press <b>Start</b>.",
		center_target: true, // app knows what this is, it displays the bullseye on the center of the screen.
		
	}
}

