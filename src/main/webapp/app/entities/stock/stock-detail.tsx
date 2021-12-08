import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './stock.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StockDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const stockEntity = useAppSelector(state => state.stock.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stockDetailsHeading">
          <Translate contentKey="mullyaApp.stock.detail.title">Stock</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{stockEntity.id}</dd>
          <dt>
            <span id="minPrice">
              <Translate contentKey="mullyaApp.stock.minPrice">Min Price</Translate>
            </span>
          </dt>
          <dd>{stockEntity.minPrice}</dd>
          <dt>
            <span id="maxPrice">
              <Translate contentKey="mullyaApp.stock.maxPrice">Max Price</Translate>
            </span>
          </dt>
          <dd>{stockEntity.maxPrice}</dd>
          <dt>
            <span id="quantityKg">
              <Translate contentKey="mullyaApp.stock.quantityKg">Quantity Kg</Translate>
            </span>
          </dt>
          <dd>{stockEntity.quantityKg}</dd>
          <dt>
            <span id="expiry">
              <Translate contentKey="mullyaApp.stock.expiry">Expiry</Translate>
            </span>
          </dt>
          <dd>{stockEntity.expiry}</dd>
          <dt>
            <span id="avialableFrom">
              <Translate contentKey="mullyaApp.stock.avialableFrom">Avialable From</Translate>
            </span>
          </dt>
          <dd>{stockEntity.avialableFrom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="mullyaApp.stock.description">Description</Translate>
            </span>
          </dt>
          <dd>{stockEntity.description}</dd>
          <dt>
            <span id="stockStatus">
              <Translate contentKey="mullyaApp.stock.stockStatus">Stock Status</Translate>
            </span>
          </dt>
          <dd>{stockEntity.stockStatus}</dd>
          <dt>
            <span id="isOpenForBidding">
              <Translate contentKey="mullyaApp.stock.isOpenForBidding">Is Open For Bidding</Translate>
            </span>
          </dt>
          <dd>{stockEntity.isOpenForBidding ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mullyaApp.stock.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{stockEntity.createdOn ? <TextFormat value={stockEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mullyaApp.stock.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{stockEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mullyaApp.stock.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{stockEntity.updatedOn ? <TextFormat value={stockEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mullyaApp.stock.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{stockEntity.updatedBy}</dd>
          <dt>
            <Translate contentKey="mullyaApp.stock.farmerAddress">Farmer Address</Translate>
          </dt>
          <dd>{stockEntity.farmerAddress ? stockEntity.farmerAddress.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.stock.farmer">Farmer</Translate>
          </dt>
          <dd>{stockEntity.farmer ? stockEntity.farmer.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.stock.category">Category</Translate>
          </dt>
          <dd>{stockEntity.category ? stockEntity.category.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.stock.variant">Variant</Translate>
          </dt>
          <dd>{stockEntity.variant ? stockEntity.variant.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.stock.subVariant">Sub Variant</Translate>
          </dt>
          <dd>{stockEntity.subVariant ? stockEntity.subVariant.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/stock" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/stock/${stockEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StockDetail;
