package de.qaware.mercury.business.shop.impl

import de.qaware.mercury.business.email.EmailService
import de.qaware.mercury.business.location.GeoLocation
import de.qaware.mercury.business.location.LocationService
import de.qaware.mercury.business.login.ShopLoginService
import de.qaware.mercury.business.shop.*
import de.qaware.mercury.business.time.Clock
import de.qaware.mercury.business.uuid.UUIDFactory
import de.qaware.mercury.storage.shop.ShopRepository
import spock.lang.Specification

import java.time.ZonedDateTime

class ShopServiceImplSpec extends Specification {

    ShopService shopService

    UUIDFactory uuidFactory = Mock()
    LocationService locationService = Mock()
    ShopRepository shopRepository = Mock()
    EmailService emailService = Mock()
    ShopLoginService shopLoginService = Mock()
    Clock clock = Mock()
    ShopServiceConfigurationProperties config = Mock()

    void setup() {
        shopService = new ShopServiceImpl(uuidFactory, locationService, shopRepository, emailService, shopLoginService, clock, config)
    }

    def "List all shops"() {
        when:
        List<Shop> all = shopService.listAll()

        then:
        1 * shopRepository.listAll() >> [new Shop.ShopBuilder().build()]
        all.size() == 1
    }

    def "Find nearby shops"() {
        setup:
        GeoLocation location = GeoLocation.of(0.0, 0.0)

        when:
        List<ShopWithDistance> nearby = shopService.findNearby('83024')

        then:
        1 * locationService.lookup('83024') >> location
        1 * shopRepository.findApproved(location) >> [new Shop.ShopBuilder().build()]
        nearby.size() == 1
    }

    def "Delete shop by ID"() {
        given:
        Shop.Id id = Shop.Id.of(UUID.randomUUID())

        when:
        shopService.delete(id)

        then:
        1 * shopRepository.findById(id) >> new Shop.ShopBuilder().build()
        1 * shopRepository.deleteById(id)
        noExceptionThrown()
    }

    def "Delete unknown shop by ID"() {
        given:
        Shop.Id id = Shop.Id.of(UUID.randomUUID())

        when:
        shopService.delete(id)

        then:
        1 * shopRepository.findById(id) >> null
        thrown ShopNotFoundException
    }

    def "Find shop by ID"() {
        given:
        Shop.Id id = Shop.Id.of(UUID.randomUUID())
        Shop shop = new Shop.ShopBuilder().id(id).build()

        when:
        Shop found = shopService.findById(id)

        then:
        1 * shopRepository.findById(id) >> shop
        found == shop
    }

    def "Can't find unknown shop by ID"() {
        given:
        Shop.Id id = Shop.Id.of(UUID.randomUUID())

        when:
        Shop found = shopService.findById(id)

        then:
        1 * shopRepository.findById(id) >> null
        found == null
    }

    def "Find shop by ID (with Exception)"() {
        given:
        Shop.Id id = Shop.Id.of(UUID.randomUUID())
        Shop shop = new Shop.ShopBuilder().id(id).build()

        when:
        Shop found = shopService.findByIdOrThrow(id)

        then:
        1 * shopRepository.findById(id) >> shop
        found == shop
        noExceptionThrown()
    }

    def "Can't find unknown shop by ID (with Exception)"() {
        given:
        Shop.Id id = Shop.Id.of(UUID.randomUUID())

        when:
        shopService.findByIdOrThrow(id)

        then:
        1 * shopRepository.findById(id) >> null
        thrown ShopNotFoundException
    }


    def "Create a new shop"() {
        given:
        UUID uuid = UUID.randomUUID()
        GeoLocation location = GeoLocation.of(0.0, 0.0)
        ZonedDateTime dateTime = ZonedDateTime.now()
        ShopCreation creation = new ShopCreation.ShopCreationBuilder()
            .email('test@lokaler.kaufen')
            .name('Test Shop')
            .zipCode('83024')
            .build()

        when:
        Shop shop = shopService.create(creation)

        then:
        1 * shopLoginService.hasLogin('test@lokaler.kaufen') >> false
        1 * uuidFactory.create() >> uuid
        1 * locationService.lookup('83024') >> location
        2 * clock.nowZoned() >> dateTime

        and:
        with(shop) {
            shop.id.id == uuid
            shop.email == creation.email
            shop.name == creation.name
            shop.geoLocation == location
            shop.updated == dateTime
            shop.created == dateTime
        }
    }

    def "Can't create an existing shop"() {
        given:
        ShopCreation creation = new ShopCreation.ShopCreationBuilder().email('test@lokaler.kaufen').build()

        when:
        shopService.create(creation)

        then:
        1 * shopLoginService.hasLogin('test@lokaler.kaufen') >> true
        thrown ShopAlreadyExistsException
    }

    def "Update shop"() {
        given:
        ShopUpdate update = new ShopUpdate.ShopUpdateBuilder().zipCode('83022').build()
        Shop.Id shopId = Shop.Id.of(UUID.randomUUID())
        Shop shop = new Shop.ShopBuilder().id(shopId).zipCode('83024').build()

        when:
        Shop updated = shopService.update(shop, update)

        then:
        1 * shopRepository.update(_)

        and:
        with(updated) {
            id == shopId
            zipCode == update.zipCode
        }
    }

    def "Can't change unknown shop"() {
        given:
        Shop.Id shopId = Shop.Id.of(UUID.randomUUID())

        when:
        shopService.changeApproved(shopId, true)

        then:
        1 * shopRepository.findById(shopId) >> null
        thrown ShopNotFoundException
    }

    def "Approve shop"() {
        given:
        Shop.Id shopId = Shop.Id.of(UUID.randomUUID())
        Shop shop = new Shop.ShopBuilder().id(shopId).approved(false).build()

        when:
        shopService.changeApproved(shopId, true)

        then:
        1 * shopRepository.findById(shopId) >> shop
        1 * shopRepository.update({ Shop s -> s.approved })
    }

    def "Send create link to email"() {
        when:
        shopService.sendCreateLink('test@lokaler.kaufen')

        then:
        shopLoginService.hasLogin('test@lokaler.kaufen') >> false
        emailService.sendShopCreationLink('test@lokaler.kaufen')
    }

    def "Find by name"() {
        given:
        Shop shop = new Shop.ShopBuilder().build()

        when:
        List<Shop> found = shopService.findByName('Test Shop')

        then:
        1 * shopRepository.findByName('Test Shop') >> [shop]
        found.size() == 1
        found[0] == shop
    }

    def "Search by query and ZIP code"() {
        given:
        GeoLocation location = GeoLocation.of(0.0, 0.0)

        when:
        List<ShopWithDistance> results = shopService.search('*', '83024')

        then:
        1 * locationService.lookup('83024') >> location
        1 * shopRepository.search('*', location) >> [new ShopWithDistance(null, 0.0)]
        results.size() == 1
    }
}
