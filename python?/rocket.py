import time

earth_mass=6*10**24 # constant
timestep = 1
G = 6.67*10**(-11) # constant
R=6.4*10**6 # constant
r=R
altitude = r-R
payload=12000 # constant
fuel1=28000
tank1=2000
fuel2=0 # change this pls
total_mass = payload + fuel1 + tank1 + fuel2
v_ex1 = 5000
v_ex2 = 7500
time = 0
velocity = 0
accel = 0
segment = 1
e=2.71828182845904523536028747135266249
liftofftime=0

def getfuelrate():
    global time
    global segment
    global fuel1
    global fuel2
    if fuel1 > 0:
        if time<10:
            return 20 * time
        else:
            return 200
    else:
        fuel1 = 0
    	if segment == 1:
    		time = 0
        segment = 2
        if fuel2 > 0:
            if 25*time+100>250:
        	    return 250
            else:
                return 100+25*time
        else:
            fuel2 = 0 
            return 0

def getthrustforce():
    global tank1
    if segment == 1:
        global fuel1
        rate = getfuelrate()
        fuel1 -= getfuelrate() * timestep
        return rate * v_ex1
    else:
        tank1 = 0
        global fuel2
        rate = getfuelrate()
        fuel2 -= rate * timestep
        return rate * v_ex2

def gravforce():
    return (G * total_mass * earth_mass) / (r ** 2)

def dragforce():
    global altitude
    b=3*(e**(-altitude/10000))
    return -b*velocity

def kinematics():
    global velocity
    global r
    global total_mass
    global altitude
    global accel
    global liftofftime
    total_mass = payload + fuel1+fuel2+tank1
    f_net=getthrustforce() - gravforce() + dragforce()
    if f_net > 0 and liftofftime == 0:
        liftofftime = time
    if f_net < 0 and altitude < 100:
        f_net = 0
    accel = f_net / total_mass
    r += velocity * timestep + .5*accel*timestep**2
    velocity = velocity + accel * timestep
    altitude = r-R

while time<1000:
    global time
    print('velocity: ' + str(velocity))
    print('time: ' + str(time))
    print('altitude: ' + str(altitude))
    print(getfuelrate()*v_ex1)
    print(accel)
    kinematics()
    time += timestep

print(G)