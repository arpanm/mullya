import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bidding-details.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BiddingDetailsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const biddingDetailsEntity = useAppSelector(state => state.biddingDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="biddingDetailsDetailsHeading">
          <Translate contentKey="mulyaaApp.biddingDetails.detail.title">BiddingDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{biddingDetailsEntity.id}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="mulyaaApp.biddingDetails.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>{biddingDetailsEntity.startDate}</dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="mulyaaApp.biddingDetails.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>{biddingDetailsEntity.endDate}</dd>
          <dt>
            <span id="biddingStatus">
              <Translate contentKey="mulyaaApp.biddingDetails.biddingStatus">Bidding Status</Translate>
            </span>
          </dt>
          <dd>{biddingDetailsEntity.biddingStatus}</dd>
          <dt>
            <span id="minPrice">
              <Translate contentKey="mulyaaApp.biddingDetails.minPrice">Min Price</Translate>
            </span>
          </dt>
          <dd>{biddingDetailsEntity.minPrice}</dd>
          <dt>
            <span id="maxPrice">
              <Translate contentKey="mulyaaApp.biddingDetails.maxPrice">Max Price</Translate>
            </span>
          </dt>
          <dd>{biddingDetailsEntity.maxPrice}</dd>
          <dt>
            <span id="minQuantityKg">
              <Translate contentKey="mulyaaApp.biddingDetails.minQuantityKg">Min Quantity Kg</Translate>
            </span>
          </dt>
          <dd>{biddingDetailsEntity.minQuantityKg}</dd>
          <dt>
            <span id="maxQuantityKg">
              <Translate contentKey="mulyaaApp.biddingDetails.maxQuantityKg">Max Quantity Kg</Translate>
            </span>
          </dt>
          <dd>{biddingDetailsEntity.maxQuantityKg}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="mulyaaApp.biddingDetails.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{biddingDetailsEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mulyaaApp.biddingDetails.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {biddingDetailsEntity.createdOn ? (
              <TextFormat value={biddingDetailsEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mulyaaApp.biddingDetails.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{biddingDetailsEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mulyaaApp.biddingDetails.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {biddingDetailsEntity.updatedOn ? (
              <TextFormat value={biddingDetailsEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mulyaaApp.biddingDetails.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{biddingDetailsEntity.updatedBy}</dd>
          <dt>
            <Translate contentKey="mulyaaApp.biddingDetails.stock">Stock</Translate>
          </dt>
          <dd>{biddingDetailsEntity.stock ? biddingDetailsEntity.stock.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bidding-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bidding-details/${biddingDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BiddingDetailsDetail;
