# import time

earth_mass = 6 * 10 ** 24  # constant
time_step = 0.01
G = 6.67 * 10 ** (-11)  # constant
earth_radius = 6.4 * 10 ** 6  # constant
r = earth_radius
alt = r - earth_radius
payload = 12000  # constant
fuel1 = 28000
tank1 = 2000
fuel2 = 10000  # change this pls
total_mass = payload + fuel1 + tank1 + fuel2
v_ex1 = 5000
v_ex2 = 7500
time = 0
velocity = 0
accel = 0
segment = 1
e = 2.71828182845904523536028747135266249
liftoff_time = 0


def burn_rate():
	global time
	global segment
	global fuel1
	global fuel2
	if fuel1 > 0:
		if time < 10:
			return 20 * time
		else:
			return 200
	else:
		fuel1 = 0
		if segment == 1:
			time = 0
		segment = 2
		if fuel2 > 0:
			if 25 * time + 100 > 250:
				return 250
			else:
				return 100 + 25 * time
		else:
			fuel2 = 0
			return 0


def thrust_force():
	global tank1
	if segment == 1:
		global fuel1
		rate = burn_rate()
		fuel1 -= burn_rate() * time_step
		return rate * v_ex1
	else:
		tank1 = 0
		global fuel2
		rate = burn_rate()
		fuel2 -= rate * time_step
		return rate * v_ex2


def grav_force():
	return (G * total_mass * earth_mass) / (r ** 2)


def drag_force():
	global alt
	b = 3 * (e ** (-alt / 10000))
	return -b * velocity ** 2  # Fd = bv**2


def kinematics():
	global velocity
	global r
	global total_mass
	global alt
	global accel
	global liftoff_time
	total_mass = payload + fuel1 + fuel2 + tank1
	f_net = thrust_force() - grav_force() + drag_force()
	if f_net > 0 and liftoff_time == 0:
		liftoff_time = time
	if f_net < 0 and alt <= 0:
		f_net = 0
		alt = 0
	accel = f_net / total_mass
	r += velocity * time_step + .5 * accel * time_step ** 2
	velocity = velocity + accel * time_step
	alt = r - earth_radius


while time < 1000:
	global time
	print()
	print('time: ' + str(time))
	print('velocity: ' + str(velocity))
	print('altitude: ' + str(alt))
	print('thrust force: ' + str(burn_rate() * v_ex1))
	print('accel: ' + str(accel))
	kinematics()
	time += time_step

print(G)
print(liftoff_time)
